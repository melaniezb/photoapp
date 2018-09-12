package com.example.melaniez.photoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.melaniez.photoapp.Admin.AdminActivity;
import com.example.melaniez.photoapp.Home.HomeFragment;
import com.example.melaniez.photoapp.Utils.BottomNavBarHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.bottom_nav_bar) BottomNavigationViewEx mNavBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupFirebaseAuth();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, HomeFragment.newInstance()).commit();
        setBottomNavigationUI(fm);
    }

    /* ************************************ Helper Methods ****************************************/
    private void setBottomNavigationUI(FragmentManager fm){
        BottomNavBarHelper helper = new BottomNavBarHelper();
        helper.setUp(mNavBar);
        helper.enableNavigation(MainActivity.this, fm, mNavBar);
    }

    /* *************************************** Firebase *******************************************/
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    Log.i(TAG, "onAuthStateChanged: logged out: transferring to the login page");
                    startActivity(new Intent(MainActivity.this, AdminActivity.class));
                } else {
                    Log.i(TAG, "onAuthStateChanged: logged in: user id =" + user.getUid());
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) mAuth.removeAuthStateListener(mAuthStateListener);
    }
}
