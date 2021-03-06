package com.estebanf.nanodegree.spotifystreamer.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.RetrofitError;

/**
 * Wrap the spotify artist model and search utilities
 */
public class Artist extends BaseItemResult implements Serializable, IItemResult{

    private ArrayList<Track> topTracks;

    public List<IItemResult> getTopTracksAsItemResult(){
        ArrayList<IItemResult> results = new ArrayList<>(topTracks.size());
        for(Track t: topTracks){
            results.add(t);
        }
        return results;
    }

    @Override
    public String getText() {
        return getName();
    }

    @Override
    public String getSmallText() {
        return null;
    }

    @Override
    public String getThumb() {
        return getImage();
    }

    @Override
    public Boolean hasThumb() {
        return hasImage();
    }


    public Artist(){
        topTracks = new ArrayList<>();
    }

    public static ArrayList<Artist> search(String term) throws Exception{
        // Create the results array. We'll return an empty array in case of no results or bad data
        ArrayList<Artist> results = new ArrayList<>();
        if(term != null && term.length() >= 3){
            try {
                // Get the service wrapper
                SpotifyService service = new SpotifyApi().getService();
                term = term.replace(' ', '+');
                ArtistsPager pager = service.searchArtists(term);
                for (kaaes.spotify.webapi.android.models.Artist item : pager.artists.items) {
                    Artist artist = new Artist();
                    artist.setName(item.name);
                    artist.setSpotifyId(item.id);
                    artist.setImage(artist.findImage(item.images));
                    results.add(artist);
                }
            }
            catch (RetrofitError retrofitError){
                throw new Exception("An error occurred",retrofitError);
            }
        }
        return results;
    }

    public boolean loadTopTracks() throws Exception{

        SpotifyService service = new SpotifyApi().getService();
        HashMap<String,Object> queryString = new HashMap<>();
        queryString.put(SpotifyService.COUNTRY, Locale.getDefault().getCountry());

        try {
            Tracks results = service.getArtistTopTrack(spotifyId, queryString);
            if (results == null || results.tracks.size() == 0) {
                return false;
            }
            for (kaaes.spotify.webapi.android.models.Track track : results.tracks) {
                Track t = new Track();
                t.setName(track.name);
                t.setSpotifyId(track.id);
                t.setImage(findImage(track.album.images));
                t.setUrl(track.preview_url);
                t.setAlbum(track.album.name);
                topTracks.add(t);
            }
            return true;
        }
        catch(RetrofitError retrofitError){
            throw new Exception("An error occurred",retrofitError);
        }
    }
}
