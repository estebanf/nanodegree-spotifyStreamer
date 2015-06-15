package com.estebanf.nanodegree.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTracksActivityFragment extends Fragment {
    private Artist artist;

    public TopTracksActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        artist = (Artist)getActivity().getIntent().getSerializableExtra("Artist");
        Log.v("spotifystreamer", artist.getName());

        return inflater.inflate(R.layout.fragment_top_tracks, container, false);
    }
}
