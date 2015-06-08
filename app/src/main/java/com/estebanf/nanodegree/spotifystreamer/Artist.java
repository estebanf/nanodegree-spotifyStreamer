package com.estebanf.nanodegree.spotifystreamer;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Wrap the spotify artist model and search utilities
 */
public class Artist {
    private String name;
    private String spotifyId;
    private String image;

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

    public static ArrayList<Artist> search(String term){
        // Create the results array. We'll return an empty array in case of no results or bad data
        ArrayList<Artist> results = new ArrayList<>();
        if(term != null && term.length() >= 3){
            // Get the service wrapper
            SpotifyService service = new SpotifyApi().getService();
            term = term.replace(' ', '+');
            ArtistsPager pager =  service.searchArtists(term);
            for(kaaes.spotify.webapi.android.models.Artist item : pager.artists.items){
                Artist artist = new Artist();
                artist.setName(item.name);
                artist.setSpotifyId(item.id);
                if(item.images != null  && item.images.size() > 0){
                    if(item.images.size() == 1){
                        artist.setImage(item.images.get(0).url);
                    }
                    else{
                        // Find the image with the smallest difference with target width
                        int targetWidth = 200;
                        int minPos = 0;
                        int minDifference = Integer.MAX_VALUE;
                        for(int j = 0; j < item.images.size(); j++){
                            Image img =  item.images.get(j);
                            int difference = Math.abs(targetWidth - img.width);
                            if(difference <= minDifference){
                                minDifference = difference;
                                minPos=j;
                            }
                            if(minDifference == 0){
                                break;
                            }
                        }
                        artist.setImage(item.images.get(minPos).url);
                    }
                }
                results.add(artist);
            }
        }
        return results;
    }
}
