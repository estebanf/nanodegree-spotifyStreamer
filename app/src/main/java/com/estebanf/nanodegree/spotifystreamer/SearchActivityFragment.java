package com.estebanf.nanodegree.spotifystreamer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estebanf.nanodegree.spotifystreamer.models.Artist;
import com.estebanf.nanodegree.spotifystreamer.models.IItemResult;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {

    private ResultListAdapter adapter;
    private ListView lView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new ResultListAdapter(getActivity(), R.layout.list_result_item,new ArrayList<IItemResult>());

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        lView = (ListView) rootView.findViewById(R.id.resultsListView);
        lView.setAdapter(adapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist currentArtist = (Artist)adapter.getItem(position);
                AsyncLoadTopTracksTask topTracksTask = new AsyncLoadTopTracksTask();
                topTracksTask.execute(currentArtist);
            }
        });


        EditText editText = (EditText)rootView.findViewById(R.id.searchText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String term = v.getText().toString();
                if(term.length() > 3) {
                    AsyncSearchTask searchTask = new AsyncSearchTask();
                    searchTask.execute(term);
                }
                return false;
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ((EditText)v).setText("");
                }
            }
        });
        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        lView.setAdapter(adapter);
    }

    private class AsyncLoadTopTracksTask extends AsyncTask<Artist,Void,Artist>{
        ProgressDialog waitDialog;
        String errorMessage;
        @Override
        protected Artist doInBackground(Artist... params) {
            Artist currentArtist = params[0];
            try{
                if(currentArtist.loadTopTracks()){
                    return currentArtist;
                }
            }
            catch (Exception ex){
                errorMessage = ex.getMessage();
                waitDialog.dismiss();
                this.cancel(true);
            }
            return null;
        }

        @Override
        protected void onCancelled(Artist artist) {
            super.onCancelled(artist);
            Toast toast = Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        protected void onPostExecute(Artist artist) {
            waitDialog.dismiss();
            if(artist == null){
                Toast toast = Toast.makeText(getActivity(),"No top tracks available",Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                Intent topTracksIntent = new Intent(getActivity(), TopTracksActivity.class)
                        .putExtra("Artist",artist);
                getActivity().startActivity(topTracksIntent);

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            waitDialog = new ProgressDialog(getActivity());
            waitDialog.setTitle("Loading");
            waitDialog.show();
        }
    }
    private class AsyncSearchTask extends AsyncTask<String,Void,List<Artist>>{
        ProgressDialog waitDialog;
        String errorMessage;
        @Override
        protected List<Artist> doInBackground(String... params) {
            String term = params[0];
            try{
                return Artist.search(term);
            }
            catch (Exception ex){
                errorMessage = ex.getMessage();
                waitDialog.dismiss();
                this.cancel(true);
            }
            return null;
        }

        @Override
        protected void onCancelled(List<Artist> artists) {
            super.onCancelled(artists);
            Toast toast = Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            adapter.clear();
            adapter.addAll(artists);
            adapter.notifyDataSetChanged();
            waitDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            waitDialog = new ProgressDialog(getActivity());
            waitDialog.setTitle("Loading");
            waitDialog.show();
        }
    }
}
