package org.mozilla.benchmark.objects;

import java.util.ArrayList;

/**
 * Created by silviu.checherita on 12/29/2017.
 */
public class ImageElement {
    private String name;
    private ArrayList<ImageDetails> imageDetails;

    public ImageElement(String name, ArrayList<ImageDetails> imageDetails) {
        this.name = name;
        this.imageDetails = imageDetails;
    }

    public ImageElement(String name) {
        this.name = name;
        this.imageDetails = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.imageDetails = new ArrayList<>();
    }

    public ArrayList<ImageDetails> getImageDetails() {
        return imageDetails;
    }

    public void setImageDetails(ArrayList<ImageDetails> imageDetails) {
        this.imageDetails = imageDetails;
    }
}
