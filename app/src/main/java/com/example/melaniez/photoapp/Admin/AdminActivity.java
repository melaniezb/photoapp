package com.example.melaniez.photoapp.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.melaniez.photoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private static final String TAG = "AdminActivity";
    private FragmentManager fm = getSupportFragmentManager();
    private LogInFragment mLogInFragment = LogInFragment.newInstance();

    @BindView(R.id.text1) TextView mReminder;
    @BindView(R.id.text2) TextView mSwitch;

    @Override
    public void onBackStackChanged() {
        if (mLogInFragment.isVisible()) {
            Log.d(TAG, "onBackStackChanged: mLogInFragment is visible.");
            mReminder.setText(R.string.activity_admin_login_text1);
            mSwitch.setText(R.string.activity_admin_login_text2);
        } else {
            Log.d(TAG, "onBackStackChanged: mSignUpFragment is visible.");
            mReminder.setText(R.string.activity_admin_signup_text1);
            mSwitch.setText(R.string.activity_admin_signup_text2);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        fm.addOnBackStackChangedListener(this);
        Fragment f = fm.findFragmentById(R.id.fragment_container);
        if (f == null) {
            fm.beginTransaction().add(R.id.fragment_container, mLogInFragment).addToBackStack("log in").commit();
        }
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLogInFragment.isVisible())
                    fm.beginTransaction().replace(R.id.fragment_container, SignUpFragment.newInstance()).addToBackStack("sign up").commit();
                else fm.popBackStack();
            }
        });
    }
}
