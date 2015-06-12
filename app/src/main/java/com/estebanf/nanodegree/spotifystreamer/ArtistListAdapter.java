package com.estebanf.nanodegree.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class ArtistListAdapter extends ArrayAdapter<Artist> {
    private int mResource;
    private Context mContext;
//    private List<Artist> artists;
    public ArtistListAdapter(Context context, int resource, List<Artist> objects) {
        super(context, resource, objects);
        mResource = resource;
        mContext = context;
//        artists = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        Artist artist = getItem(position);
        if(convertView == null){
            layout = new LinearLayout(getContext());
            ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(mResource,layout,true);
        }
        else{
            layout = (LinearLayout)convertView;
        }
        ((TextView)layout.findViewById(R.id.textView)).setText(artist.getName());
        if(artist.hasThumb()){
            Picasso.with(mContext).load(artist.getImage()).into(((ImageView)layout.findViewById(R.id.imageView)));
        }
        return layout;
    }
}
