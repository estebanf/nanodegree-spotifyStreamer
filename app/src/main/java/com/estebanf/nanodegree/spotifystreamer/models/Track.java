package com.estebanf.nanodegree.spotifystreamer.models;

import java.io.Serializable;

/**
 * Created by estebanf on 6/15/15.
 */
public class Track extends BaseItemResult implements Serializable, IItemResult{
    private String url;
    private String album;

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

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

    @Override
    public String getSmallText() {
        return getAlbum();
    }
}
