package com.estebanf.nanodegree.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.estebanf.nanodegree.spotifystreamer.models.Artist;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTracksActivityFragment extends Fragment {
    private Artist artist;
    private ResultListAdapter adapter;
    private ListView lView;
    public TopTracksActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        artist = (Artist)getActivity().getIntent().getSerializableExtra("Artist");
        adapter = new ResultListAdapter(getActivity(),R.layout.list_result_item,artist.getTopTracksAsItemResult());
        View rootView = inflater.inflate(R.layout.fragment_top_tracks,container,false);
        lView = (ListView)rootView.findViewById(R.id.topTracksListView);
        lView.setAdapter(adapter);

        return rootView;
    }
}
