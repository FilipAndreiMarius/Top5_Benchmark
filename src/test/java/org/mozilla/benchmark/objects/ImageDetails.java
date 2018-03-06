package org.mozilla.benchmark.objects;

import java.awt.*;

/**
 * Created by silviu.checherita on 12/29/2017.
 */
public class ImageDetails {

    private String name;
    private Rectangle rectangle;
    private ImageSearchTypes searchType;
    private float similarity;

    public ImageDetails(String name, Rectangle rectangle, ImageSearchTypes searchType, float similarity) {
        this.name = name;
        this.rectangle = rectangle;
        this.searchType = searchType;
        this.similarity = similarity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public ImageSearchTypes getSearchType() {
        return searchType;
    }

    public void setSearchType(ImageSearchTypes searchType) {
        this.searchType = searchType;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }
}
