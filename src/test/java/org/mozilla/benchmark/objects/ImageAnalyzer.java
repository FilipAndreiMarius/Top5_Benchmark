package org.mozilla.benchmark.objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.utils.Constants;
import org.sikuli.script.Finder;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.io.FileUtils.iterateFiles;

/**
 * Created by Silviu on 11/12/2017.
 */
public class ImageAnalyzer {

    private static final Logger logger = LogManager.getLogger(ImageAnalyzer.class.getName());

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

    private ArrayList<ImagePattern> initializePatterns(String testName) {

        ArrayList<ImagePattern> patternList = new ArrayList<>();
        String jsonPath = Constants.Paths.PATTERNS_PATH + "\\" + testName + "\\" + testName.toLowerCase() + ".json";

        for (int i = 0; i < Constants.Execution.NUMBER_OF_RUNS; i++) {
            try {
                ImagePattern pattern = new Gson().fromJson(new FileReader(jsonPath), ImagePattern.class);
                pattern.setName(testName + "_run_" + (i + 1));
                patternList.add(pattern);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return patternList;
    }

    private ArrayList<String> initializeImages(String testName) {

        ArrayList<String> images = new ArrayList<>();
        Iterator it = iterateFiles(new File(Constants.Paths.SPLIT_VIDEO_PATH + "\\" + testName), null, false);
        while (it.hasNext()) {
            Object o = it.next();
            images.add(o.toString());
        }
        return images;
    }

    private Boolean searchImage(String imagePath1, String imagePath2, float similarity) {
        try {
            Finder finder = new Finder(imagePath1, new Region(286, 164, 108, 23));
            Pattern pattern = new Pattern(imagePath2).similar(similarity);
            finder.find(pattern);
            return finder.hasNext();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
                        String patternPath = Constants.Paths.PATTERNS_PATH + "\\" + testName + "\\" + element.getImageDetails().get(j).getName();
                        logger.info(k + " - [" + element.getName() + "] - Searching for pattern " + patternPath +
                                " in " + images.get(k));
                        if (searchImage(images.get(k), patternPath, element.getImageDetails().get(j).getSimilarity())) {
                            if (element.getImageDetails().size() - pattern_counter > 1) {
                                pattern_counter = j + 1;
                            } else {
                                result.addProperty(element.getName(), image_counter + 1);
                                pattern_counter = 0;
                                this.lastFound = image_counter + 1;
                            }
                            image_counter = k + 1;
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

    public void setLastFound(int lastFound) {
        this.lastFound = lastFound;
    }

    public int getLastFound() {
        return this.lastFound;
    }
}


