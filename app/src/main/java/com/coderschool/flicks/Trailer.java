package com.coderschool.flicks;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }
}
