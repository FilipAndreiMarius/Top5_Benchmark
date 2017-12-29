package org.mozilla.benchmark.objects;

import java.util.ArrayList;

/**
 * Created by silviu.checherita on 12/29/2017.
 */
public class ImagePattern {

    private String name;
    private ArrayList<ImageElement> imageElements;

    public ImagePattern(String name, ArrayList<ImageElement> imageElements){
        this.name = name;
        this.imageElements = imageElements;
    }

    public ImagePattern(String name){
        this.name = name;
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
}
