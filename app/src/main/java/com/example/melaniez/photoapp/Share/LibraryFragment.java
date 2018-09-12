package com.example.melaniez.photoapp.Share;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.melaniez.photoapp.R;

public class LibraryFragment extends Fragment {

    public static LibraryFragment newInstance(){
        return new LibraryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, parent, false);
        return view;
    }
}
