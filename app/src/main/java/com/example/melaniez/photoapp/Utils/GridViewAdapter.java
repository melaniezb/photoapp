package com.example.melaniez.photoapp.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.melaniez.photoapp.R;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<String> {
    private LayoutInflater mInflater;

    private Context mContext;
    ArrayList<String> mImgUrls;

    public GridViewAdapter(Context context, ArrayList<String> imgUrls){
        super(context, R.layout.single_post_view, imgUrls);
        mContext = context;
        mImgUrls = imgUrls;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.single_post_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = convertView.findViewById(R.id.single_post);
            viewHolder.mProgressBar = convertView.findViewById(R.id.progress_bar);

            convertView.setTag(viewHolder);
            // tag is a way to store the widgets of viewHolder in memory when they are not displayed
            // on the screen to prevent the app to slow down.
        } else viewHolder = (ViewHolder) convertView.getTag();

        MyImageLoader.setImage(getItem(position), viewHolder.mImageView, viewHolder.mProgressBar, "");

        return convertView;
    }

    private class ViewHolder{
        ImageView mImageView;
        ProgressBar mProgressBar;
    }
}
