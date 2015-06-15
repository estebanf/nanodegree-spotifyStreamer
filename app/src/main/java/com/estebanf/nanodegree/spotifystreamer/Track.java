package com.estebanf.nanodegree.spotifystreamer;

import java.io.Serializable;

/**
 * Created by estebanf on 6/15/15.
 */
public class Track extends BaseItemResult implements Serializable, IItemResult{
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getText() {
        return getName();
    }

    @Override
    public String getThumb() {
        return getImage();
    }

    @Override
    public Boolean hasThumb() {
        return hasImage();
    }
}
