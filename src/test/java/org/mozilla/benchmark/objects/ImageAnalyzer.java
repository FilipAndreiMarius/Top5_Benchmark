package org.mozilla.benchmark.objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mozilla.benchmark.constants.ExecutionConstants;
import org.mozilla.benchmark.constants.FileExtensionsConstants;
import org.mozilla.benchmark.constants.PathConstants;
import org.mozilla.benchmark.utils.FileManager;
import org.mozilla.benchmark.utils.ImagePatternUtils;
import org.mozilla.benchmark.utils.LoggerManager;
import org.mozilla.benchmark.utils.PropertiesManager;
import org.sikuli.script.Finder;
import org.sikuli.script.Pattern;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static org.apache.commons.io.FileUtils.iterateFiles;

/**
 * Created by Silviu on 11/12/2017.
 */
public class ImageAnalyzer {

    private static final LoggerManager logger = new LoggerManager(ImageAnalyzer.class.getName());

    private ArrayList<ImagePattern> patterns;
    private ArrayList<String> images;
    private JsonObject results;
    private int lastFound;

    ImageAnalyzer(String testName) {
        this.patterns = initializePatterns(testName);
        this.images = initializeImages(testName);
        this.results = analyzeAndShowResults(testName);
        this.lastFound = 0;
    }

    private static ArrayList<ImagePattern> initializePatterns(String testName) {

        ArrayList<ImagePattern> patternList = new ArrayList<>();
        ImagePattern pattern;

        for (int i = 0; i < ExecutionConstants.NUMBER_OF_RUNS; i++) {
            try {
                if (!PropertiesManager.getDynamicPatterns()) {
                    String jsonPath = PathConstants.RESOURCES_PATH + File.separator + testName.toLowerCase() + FileExtensionsConstants.JSON_EXTENSION;
                    pattern = new Gson().fromJson(new FileReader(jsonPath), ImagePattern.class);
                } else {
                    pattern = new Gson().fromJson(new Gson().toJson(ImagePatternUtils.getInstance()), ImagePattern.class);
                }
                pattern.setName(testName + "_run_" + (i + 1));
                patternList.add(pattern);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ImagePatternUtils.closeInstance();
        return patternList;
    }

    private static Boolean searchImage(String imagePath1, String imagePath2, Rectangle rectangle, ImageSearchTypes searchType, float similarity) {
        switch (searchType) {
            case BACKGROUND_POSITIVE: {
                return backgroundColorSearch(imagePath1, imagePath2, rectangle, true);
            }
            case BACKGROUND_NEGATIVE: {
                return backgroundColorSearch(imagePath1, imagePath2, rectangle, false);
            }
            case POSITIVE: {
                try {
                    Pattern pattern = new Pattern(imagePath1).similar(similarity);
                    Finder finder = new Finder(imagePath2);
                    finder.find(pattern);
                    return finder.hasNext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case NEGATIVE: {
                try {
                    Pattern pattern = new Pattern(imagePath1).similar(similarity);
                    Finder finder = new Finder(imagePath2);
                    finder.find(pattern);
                    return !finder.hasNext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            default:
                return false;
        }
    }

    private static Boolean backgroundColorSearch(String imagePath1, String imagePath2, Rectangle rectangle, Boolean isPositiveSearch) {
        try {
            BufferedImage pattern = ImageIO.read(new File(imagePath1));
            Color colorPattern = new Color(pattern.getRGB(0, 0));
            BufferedImage image = ImageIO.read(new File(imagePath2));

            for (int i = (rectangle == null ? 0 : rectangle.x); i < (rectangle == null ? image.getWidth() : rectangle.x + rectangle.width); i++) {
                for (int j = (rectangle == null ? 0 : rectangle.y); j < (rectangle == null ? image.getHeight() : rectangle.y + rectangle.height); j++) {
                    Color colorImage = new Color(image.getRGB(i, j));
                    if ((colorImage.getRed() == colorPattern.getRed()) && (colorImage.getGreen() == colorPattern.getGreen()) &&
                            (colorImage.getBlue() == colorPattern.getBlue())) {
                        return isPositiveSearch;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return !isPositiveSearch;
    }

    private Boolean isImagePresentInPatternsFolder(String testName, ImageDetails image) {
        return FileManager.fileFound(image.getName(), PathConstants.PATTERNS_PATH + File.separator + testName);
    }

    private ArrayList<ImagePattern> validatePatterns(ArrayList<ImagePattern> initialImagePatternList, String testName) {

        logger.log(LoggerManagerLevel.INFO, "Validating patterns ...", false);
        Iterator imagePatternIterator = initialImagePatternList.iterator();
        while (imagePatternIterator.hasNext()) {
            ImagePattern imagePattern = (ImagePattern) imagePatternIterator.next();
            Iterator imageElementIterator = imagePattern.getImageElements().iterator();
            while (imageElementIterator.hasNext()) {
                ImageElement imageElement = (ImageElement) imageElementIterator.next();
                Iterator imagesDetailsIterator = imageElement.getImageDetails().iterator();
                while (imagesDetailsIterator.hasNext()) {
                    ImageDetails image = (ImageDetails) imagesDetailsIterator.next();
                    if (!isImagePresentInPatternsFolder(testName, image)) {
                        logger.log(LoggerManagerLevel.WARN, "[" + image.getName() + "] NOT found in " + PathConstants.PATTERNS_PATH + File.separator + testName + " folder. Removing from [" + imageElement.getName() + "] element", false);
                        imagesDetailsIterator.remove();
                    } else {
                        logger.log(LoggerManagerLevel.DEBUG, "[" + image.getName() + "] found!", false);
                    }
                }
                if (imageElement.getImageDetails().size() == 0) {
                    logger.log(LoggerManagerLevel.WARN, "[" + imageElement.getName() + "] has no patterns. Removing element", false);
                    imageElementIterator.remove();
                }
            }
        }
        logger.log(LoggerManagerLevel.INFO, "Pattern validation complete!", false);
        return initialImagePatternList;
    }

    private ArrayList<String> initializeImages(String testName) {

        ArrayList<String> images = new ArrayList<>();
        Iterator it = iterateFiles(new File(PathConstants.SPLIT_VIDEO_PATH + File.separator + testName), null, false);
        while (it.hasNext()) {
            Object o = it.next();
            images.add(o.toString());
        }
        return images;
    }

    private JsonObject analyzeAndShowResults(String testName) {

        ArrayList<ImagePattern> patterns = this.patterns;
        ArrayList<String> images = this.images;

        JsonObject results = new JsonObject();
        int image_counter = this.lastFound;
        int pattern_counter = 0;

        for (ImagePattern pattern : patterns) {
            JsonObject result = new JsonObject();
            for (ImageElement element : pattern.getImageElements()) {
                for (int j = pattern_counter; j < element.getImageDetails().size(); j++) {
                    for (int k = image_counter; k < images.size(); k++) {
                        String patternPath = PathConstants.PATTERNS_PATH + File.separator + testName + File.separator + element.getImageDetails().get(j).getName();
                        logger.log(LoggerManagerLevel.INFO, k + " - [" + element.getName() + "] - Searching for pattern [" + patternPath +
                                "] in [" + images.get(k) + "]", false);
                        ImageDetails imageDetails = element.getImageDetails().get(j);
                        if (searchImage(patternPath, images.get(k), imageDetails.getRectangle(), imageDetails.getSearchType(), imageDetails.getSimilarity())) {
                            if (element.getImageDetails().size() - pattern_counter > 1) {
                                pattern_counter = j + 1;
                            } else {
                                if (!("startingPoint").equals(element.getName())) {
                                    result.addProperty(element.getName(), image_counter + 1);
                                }
                                pattern_counter = 0;
                                this.lastFound = image_counter + 1;
                            }
                            break;
                        } else {
                            image_counter = k + 1;
                        }
                    }
                }
            }
            results.add(pattern.getName(), result);
        }
        return results;
    }

    public JsonObject getResults() {
        return this.results;
    }

    public ArrayList<ImagePattern> getPatterns() {
        return this.patterns;
    }

    public ArrayList<String> getImages() {
        return this.images;
    }

    public int getLastFound() {
        return this.lastFound;
    }

    public void setLastFound(int lastFound) {
        this.lastFound = lastFound;
    }
}


