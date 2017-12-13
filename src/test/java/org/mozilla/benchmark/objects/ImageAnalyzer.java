package org.mozilla.benchmark.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.benchmark.Runner;
import org.mozilla.benchmark.utils.Constants;
import org.sikuli.script.Finder;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.io.FileUtils.iterateFiles;

/**
 * Created by Silviu on 11/12/2017.
 */
public class ImageAnalyzer {

    private static final Logger logger = LogManager.getLogger(ImageAnalyzer.class.getName());

    private LinkedHashMap<String, ArrayList<String>> patterns;
    private ArrayList<String> images;
    private JsonArray results;
    private int lastFound;

    ImageAnalyzer(List<String> allElements, String testName) {
        this.patterns = initializePatterns(allElements, testName);
        this.images = initializeImages(testName);
        this.results = analyzeAndShowResults();
        this.lastFound = 0;
    }

    private LinkedHashMap<String, ArrayList<String>> initializePatterns(List<String> allElements, String testName) {

        LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap<>();

        File patternFolder = new File(Constants.Paths.PATTERNS_PATH + "\\" + testName);
        for (int i = 0; i < Constants.General.NUMBER_OF_RUNS; i++) {
            for (String element : allElements) {
                ArrayList<String> categoryImages = new ArrayList<>();
                File[] files = patternFolder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().contains(element))
                            categoryImages.add(file.getPath());
                    }
                }
                map.put(element + "_run_" + (i+1), categoryImages);
            }
        }
        return map;
    }

    private static ArrayList<String> initializeImages(String testName) {

        ArrayList<String> images = new ArrayList<>();
        Iterator it = iterateFiles(new File(Constants.Paths.SPLIT_VIDEO_PATH + "\\" + testName), null, false);
        while (it.hasNext()) {
            Object o = it.next();
            images.add(o.toString());
        }
        return images;
    }

    private static Boolean searchImage(String imagePath1, String imagePath2, float similarity) {
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

    private JsonArray analyzeAndShowResults() {

        Map<String, ArrayList<String>> patternsMap = this.patterns;
        ArrayList<String> images = this.images;

        JsonArray results = new JsonArray();
        int image_counter = this.lastFound;
        int pattern_counter = 0;

        for (Map.Entry<String, ArrayList<String>> patterns : patternsMap.entrySet()) {
            for (int j = pattern_counter; j < patterns.getValue().size(); j++) {
                for (int k = image_counter; k < images.size(); k++) {
                    logger.info(k + " - [" + patterns.getKey() + "] - Searching for pattern " + patterns.getValue().get(j) +
                            " in " + images.get(k));
                    if (searchImage(images.get(k), patterns.getValue().get(j), 0.95f)) {
                        if (patterns.getValue().size() - pattern_counter > 1) {
                            pattern_counter = j + 1;
                        } else {
                            JsonObject result = new JsonObject();
                            result.addProperty(patterns.getKey(), image_counter + 1);
                            results.add(result);
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
        return results;
    }

    public JsonArray getResults() {
        return this.results;
    }

    public LinkedHashMap<String, ArrayList<String>> getPatterns() {
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


