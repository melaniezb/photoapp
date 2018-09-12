package com.example.melaniez.photoapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;

import com.example.melaniez.photoapp.Home.HomeFragment;
import com.example.melaniez.photoapp.LikesFragment;
import com.example.melaniez.photoapp.Profile.ProfileFragment;
import com.example.melaniez.photoapp.R;
import com.example.melaniez.photoapp.SearchFragment;
import com.example.melaniez.photoapp.Share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavBarHelper {
    private static final String TAG = "BottomNavBarHelper";


    public void setUp(BottomNavigationViewEx navBar){
        Log.d(TAG, "setUp()");
        navBar.enableAnimation(false);
        navBar.enableItemShiftingMode(false);
        navBar.enableShiftingMode(false);
        navBar.setTextVisibility(false);
    }

    public void enableNavigation(final Context context, final FragmentManager fm, BottomNavigationViewEx navBar) {
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.ic_home:
                        Log.d(TAG, "onNavigationItemSelected: home fragment");
                        fm.beginTransaction().replace(R.id.fragment_container, HomeFragment.newInstance()).commit();
                        return true;

                    case R.id.ic_search:
                        Log.d(TAG, "onNavigationItemSelected: search fragment");
                        fm.beginTransaction().replace(R.id.fragment_container, SearchFragment.newInstance()).commit();
                        return true;

                    case R.id.ic_add:
                        Log.d(TAG, "onNavigationItemSelected: share fragment");
                        context.startActivity(new Intent(context, ShareActivity.class));
                        return true;

                    case R.id.ic_likes:
                        Log.d(TAG, "onNavigationItemSelected: likes fragment");
                        fm.beginTransaction().replace(R.id.fragment_container, LikesFragment.newInstance()).commit();
                        return true;

                    case R.id.ic_profile:
                        Log.d(TAG, "onNavigationItemSelected: profile fragment");
                        fm.beginTransaction().replace(R.id.fragment_container, ProfileFragment.newInstance()).commit();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}
