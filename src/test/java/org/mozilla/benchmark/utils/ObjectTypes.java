package org.mozilla.benchmark.utils;

/**
 * Created by andrei.filip on 11/22/2017.
 */


public enum ObjectTypes {
    GOOGLE("Gsearch"),
    AMAZON("Amazon"),
    GMAIL("Gmail"),
    FACEBOOK("Facebook"),
    YOUTUBE("Youtube");

    public String name;

    ObjectTypes(String name) {
        this.name = name;
    }

    public static void main(String args[]) {
        System.out.println(ObjectTypes.AMAZON.name);
    }
}


