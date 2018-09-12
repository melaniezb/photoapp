package com.example.melaniez.photoapp.Share;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.melaniez.photoapp.R;

public class PhotoFragment extends Fragment {

    public static PhotoFragment newInstance(){
        return new PhotoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, parent, false);
        return view;
    }
}
