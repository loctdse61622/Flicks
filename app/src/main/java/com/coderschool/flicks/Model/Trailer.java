package com.coderschool.flicks.Model;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }
}
