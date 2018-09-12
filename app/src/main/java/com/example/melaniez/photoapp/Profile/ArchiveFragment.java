package com.example.melaniez.photoapp.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.melaniez.photoapp.R;

public class ArchiveFragment extends Fragment {

    public static ArchiveFragment newInstance() {

        Bundle args = new Bundle();

        ArchiveFragment fragment = new ArchiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archive, container, false);
        return view;
    }
}
