package com.estebanf.nanodegree.spotifystreaner.tests;

import android.test.InstrumentationTestCase;

import com.estebanf.nanodegree.spotifystreamer.models.Artist;

import java.util.List;

/**
 * Unit tests for the stage 0 app
 */
public class SpotifyWrapperTest extends InstrumentationTestCase {
    public void testArtistModel() throws Exception{
        Artist artist = new Artist();
        artist.setName("estebanf");
        artist.setSpotifyId("123123");
        artist.setImage("some image url");
        assertEquals("estebanf",artist.getName());
        assertEquals("123123",artist.getSpotifyId());
        assertEquals("some image url",artist.getImage());
    }

    public void testEmptySearch() throws Exception{
        List<Artist> artists = Artist.search("nonononono");
        assertEquals(artists.size(),0);
    }
    public void testSearchWithResults() throws Exception{
        List<Artist> artists = Artist.search("No doubt");
        assertEquals(5,artists.size());
        Artist artist = artists.get(0);
        assertEquals("No Doubt",artist.getName());
        assertEquals("0cQbJU1aAzvbEmTuljWLlF",artist.getSpotifyId());
        assertEquals("https://i.scdn.co/image/6d74978edc9ec01cec160888e62d986b80aaea51",artist.getImage());
    }
}
