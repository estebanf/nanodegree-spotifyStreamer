package com.estebanf.nanodegree.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estebanf.nanodegree.spotifystreamer.models.IItemResult;
import com.squareup.picasso.Picasso;

import java.util.List;

class ResultListAdapter extends ArrayAdapter<IItemResult> {
    private int mResource;
    private Context mContext;
    public ResultListAdapter(Context context, int resource, List<IItemResult> objects) {
        super(context, resource, objects);
        mResource = resource;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        IItemResult result = getItem(position);
        if(convertView == null){
            layout = new LinearLayout(getContext());
            ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(mResource,layout,true);
        }
        else{
            layout = (LinearLayout)convertView;
        }
        ((TextView)layout.findViewById(R.id.textView)).setText(result.getText());
        TextView smallTextView = ((TextView)layout.findViewById(R.id.smallTextView));
        String smallText = result.getSmallText();
        if(smallText != null){
            smallTextView.setText(smallText);
        }
        else{
            smallTextView.setVisibility(View.INVISIBLE);
        }
        if(result.hasThumb()){
            Picasso.with(mContext).load(result.getThumb()).into(((ImageView) layout.findViewById(R.id.imageView)));
        }
        return layout;
    }
}
