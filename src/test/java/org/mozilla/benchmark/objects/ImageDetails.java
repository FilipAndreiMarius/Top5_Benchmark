package org.mozilla.benchmark.objects;

/**
 * Created by silviu.checherita on 12/29/2017.
 */
public class ImageDetails {

    private String name;
    private float similarity;

    public ImageDetails(String name, float similarity) {
        this.name = name;
        this.similarity = similarity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }
}
