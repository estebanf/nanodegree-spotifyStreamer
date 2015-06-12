package com.estebanf.nanodegree.spotifystreamer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {

    private ArtistListAdapter adapter;
    private ListView lView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new ArtistListAdapter(getActivity(), R.layout.list_result_item,new ArrayList<Artist>());

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        lView = (ListView) rootView.findViewById(R.id.resultsListView);
        lView.setAdapter(adapter);


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
    private class AsyncSearchTask extends AsyncTask<String,Void,List<Artist>>{
        ProgressDialog waitDialog;
        @Override
        protected List<Artist> doInBackground(String... params) {
            String term = params[0];
            return Artist.search(term);
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
