package com.coderschool.flicks.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListTrailer {
    @SerializedName("results")
    private List<Trailer> list;

    public List<Trailer> getList() {
        return list;
    }
}
