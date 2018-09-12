package com.example.melaniez.photoapp.Home;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.melaniez.photoapp.R;
import com.example.melaniez.photoapp.Utils.MyImageLoader;
import com.example.melaniez.photoapp.Utils.TabsPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    private Context mContext;
    private TabsPagerAdapter mAdapter;
    @BindView(R.id.home_top_tabs) TabLayout mTabLayout;
    @BindView(R.id.view_pager) ViewPager mViewPager;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, parent, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        setupViewPager();
        setupTabs();

        // initialize the image loader for the whole app once and for all
        MyImageLoader myImageLoader = new MyImageLoader(mContext);
        ImageLoader.getInstance().init(myImageLoader.getConfig());

        return view;
    }

    /* ************************************ Helper Methods ****************************************/
    private void setupViewPager() {
        mAdapter = new TabsPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(CameraFragment.newInstance());
        mAdapter.addFragment(FeedFragment.newInstance());
        mAdapter.addFragment(MessagesFragment.newInstance());
        mViewPager.setAdapter(mAdapter);
    }

    private void setupTabs() {
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        LinearLayout layout0 = (LinearLayout) ((LinearLayout) mTabLayout.getChildAt(0)).getChildAt(0); // public View ViewGroup.getChildAt (int index)
        LinearLayout.LayoutParams params0 = (LinearLayout.LayoutParams) layout0.getLayoutParams();
        params0.weight = 0.2f;
        //params0.width = LinearLayout.LayoutParams.WRAP_CONTENT; // works but makes the width too wide (w = 0.2f) or too narrow (w = 0)

        mTabLayout.getTabAt(1).setCustomView(R.layout.snippet_logo);
        LinearLayout layout1 = (LinearLayout) ((LinearLayout) mTabLayout.getChildAt(0)).getChildAt(1);
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) layout1.getLayoutParams();
        params1.weight = 1;
        //params1.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_messages);
        LinearLayout layout2 = (LinearLayout) ((LinearLayout) mTabLayout.getChildAt(0)).getChildAt(2); // public View ViewGroup.getChildAt (int index)
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) layout2.getLayoutParams();
        params2.weight = 0.2f;
        //params2.width = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
