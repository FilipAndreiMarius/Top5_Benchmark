package org.mozilla.benchmark.objects;

import org.mozilla.benchmark.utils.ImagePatternUtils;

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

    public static void createDynamicPattern(String testName, String elementName, String imageDetailsName, ImageSearchTypes searchType) {
        ImagePattern imagePattern = ImagePatternUtils.getInstance();
        if (!imagePattern.getName().equals(testName)) {
            imagePattern.setName(testName);
        }
        if (imagePattern.getImageElements().size() == 0) {
            addImageElementsToImagePattern(imagePattern, elementName, imageDetailsName, searchType);
        } else {
            ImageElement imageElementFound = findImageElementFromList(elementName, imagePattern.getImageElements());
            if (imageElementFound != null) {
                addImageDetailsToImageElement(imageElementFound, imageDetailsName, searchType);
            } else {
                addImageElementsToImagePattern(imagePattern, elementName, imageDetailsName, searchType);
            }
        }
    }

    private static void addImageDetailsToImageElement(ImageElement imageElement, String imageDetailsName, ImageSearchTypes searchType) {
        ImageDetails imageDetails = new ImageDetails(imageDetailsName, searchType, 0.95f);
        imageElement.getImageDetails().add(imageDetails);
    }

    private static void addImageElementsToImagePattern(ImagePattern imagePattern, String elementName, String imageDetailsName, ImageSearchTypes searchType) {
        ImageElement imageElement = new ImageElement(elementName);
        addImageDetailsToImageElement(imageElement, imageDetailsName, searchType);
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


    public String toString() {
        ImagePattern imagePattern = ImagePatternUtils.getInstance();
        System.out.println(imagePattern.getName());
        if (imagePattern.getImageElements().size() > 0) {
            for (ImageElement imageElement : imagePattern.getImageElements()) {
                System.out.println(imageElement.getName());
                if (imageElement.getImageDetails().size() > 0) {
                    for (ImageDetails imageDetails : imageElement.getImageDetails()) {
                        System.out.println(imageDetails.getName() + " - " + imageDetails.getSearchType());
                    }
                }
            }
        }
        return "";
    }
}
