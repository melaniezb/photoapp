package com.example.melaniez.photoapp.Share;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.melaniez.photoapp.R;
import com.example.melaniez.photoapp.Utils.TabsPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShareActivity extends AppCompatActivity {
    TabsPagerAdapter mAdapter;

    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);

        setupViewPager();
        setupTabLayout();
    }

    /* ************************************ Helper Methods ****************************************/

    private void setupViewPager() {
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(LibraryFragment.newInstance());
        mAdapter.addFragment(PhotoFragment.newInstance());
        mAdapter.addFragment(VideoFragment.newInstance());
        mViewPager.setAdapter(mAdapter);
    }

    private void setupTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("Library");
        mTabLayout.getTabAt(1).setText("Photo");
        mTabLayout.getTabAt(2).setText("Video");
    }


}
