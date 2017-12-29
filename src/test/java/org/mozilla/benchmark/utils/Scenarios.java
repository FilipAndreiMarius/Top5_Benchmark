package org.mozilla.benchmark.utils;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andrei.filip on 11/22/2017.
 */


public enum Scenarios {
    GOOGLE("google"),
    AMAZON("amazon"),
    GMAIL("gmail"),
    FACEBOOK("facebook"),
    YOUTUBE("youtube");


    Scenarios(String name) {
        this.name = name;
    }

    private String name;

    private static final Map<String,Scenarios> ENUM_MAP;


    public String getName() {
        return this.name;
    }

    // Build an immutable map of String name to enum pairs.
    // Any Map impl can be used.

    static {
        Map<String,Scenarios> map = new ConcurrentHashMap<>();
        for (Scenarios instance : Scenarios.values()) {
            map.put(instance.getName(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }
}


