package com.example.rusha.bakeryapp;

/**
 * Created by rusha on 6/27/2017.
 */

public class Steps {
    int id;
    String shortDesc;
    String Description;
    String videourl;
    String thumbnail;

    public String getThumbnail() {
        return thumbnail;
    }

    public String getVideourl() {

        return videourl;
    }

    public String getDescription() {

        return Description;
    }

    public String getShortDesc() {

        return shortDesc;
    }

    public int getId() {

        return id;
    }

    public Steps(int id, String shortDesc, String Description, String videourl, String thumbnail) {
        this.id = id;
        this.shortDesc = shortDesc;
        this.Description = Description;
        this.videourl = videourl;
        this.thumbnail = thumbnail;
    }
}

