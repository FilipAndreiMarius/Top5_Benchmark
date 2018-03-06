package org.mozilla.benchmark.objects;

import org.mozilla.benchmark.utils.ImagePatternUtils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by silviu.checherita on 12/29/2017.
 */
public class ImagePattern {

    private String name;
    private ArrayList<ImageElement> imageElements;

    public ImagePattern(String name, ArrayList<ImageElement> imageElements) {
        this.name = name;
        this.imageElements = imageElements;
    }

    public ImagePattern(String name) {
        this.name = name;
        this.imageElements = new ArrayList<ImageElement>();
    }

    public ImagePattern() {
        this.name = "";
        this.imageElements = new ArrayList<ImageElement>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ImageElement> getImageElements() {
        return imageElements;
    }

    public void setImageElements(ArrayList<ImageElement> imageElements) {
        this.imageElements = imageElements;
    }

    public static void createDynamicPattern(String testName, Rectangle rectangle, String elementName, String imageDetailsName, ImageSearchTypes searchType, float similarity) {
        ImagePattern imagePattern = ImagePatternUtils.getInstance();
        if (!imagePattern.getName().equals(testName)) {
            imagePattern.setName(testName);
        }
        if (imagePattern.getImageElements().size() == 0) {
            addImageElementsToImagePattern(imagePattern, elementName, imageDetailsName, rectangle, searchType, similarity);
        } else {
            ImageElement imageElementFound = findImageElementFromList(elementName, imagePattern.getImageElements());
            if (imageElementFound != null) {
                addImageDetailsToImageElement(imageElementFound, imageDetailsName, rectangle, searchType, similarity);
            } else {
                addImageElementsToImagePattern(imagePattern, elementName, imageDetailsName, rectangle, searchType, similarity);
            }
        }
    }

    private static void addImageDetailsToImageElement(ImageElement imageElement, String imageDetailsName, Rectangle rectangle, ImageSearchTypes searchType, float similarity) {
        ImageDetails imageDetails = new ImageDetails(imageDetailsName, rectangle, searchType, similarity);
        imageElement.getImageDetails().add(imageDetails);
    }

    private static void addImageElementsToImagePattern(ImagePattern imagePattern, String elementName, String imageDetailsName, Rectangle rectangle, ImageSearchTypes searchType, float similarity) {
        ImageElement imageElement = new ImageElement(elementName);
        addImageDetailsToImageElement(imageElement, imageDetailsName, rectangle, searchType, similarity);
        imagePattern.getImageElements().add(imageElement);
    }

    private static ImageElement findImageElementFromList(String elementName, ArrayList<ImageElement> imageElements) {
        for (ImageElement imageElement : imageElements) {
            if (imageElement.getName().equals(elementName)) {
                return imageElement;
            }
        }
        return null;
    }
}