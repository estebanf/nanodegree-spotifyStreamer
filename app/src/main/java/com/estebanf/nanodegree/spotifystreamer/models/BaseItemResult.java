package com.estebanf.nanodegree.spotifystreamer.models;

import com.estebanf.nanodegree.spotifystreamer.App;
import com.estebanf.nanodegree.spotifystreamer.R;

import java.io.Serializable;
import java.util.List;

import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by estebanf on 6/15/15.
 */
public abstract class BaseItemResult implements Serializable{
    protected String name;
    protected String spotifyId;
    protected String image;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    protected boolean hasImage(){
        return image != null && image.length() > 0;
    }


    protected String findImage(List<Image> images){
        if(images != null  && images.size() > 0){
            if(images.size() == 1){
                return images.get(0).url;
            }
            else{
                // Find the image with the smallest difference with target width
                int targetWidth = Integer.parseInt(App.context().getString(R.string.thumbTargetWidth));
                int minPos = 0;
                int minDifference = Integer.MAX_VALUE;
                for(int j = 0; j < images.size(); j++){
                    Image img =  images.get(j);
                    int difference = Math.abs(targetWidth - img.width);
                    if(difference <= minDifference){
                        minDifference = difference;
                        minPos=j;
                    }
                    if(minDifference == 0){
                        break;
                    }
                }
                return images.get(minPos).url;
            }
        }
        else{
            return "";
        }
    }
}
