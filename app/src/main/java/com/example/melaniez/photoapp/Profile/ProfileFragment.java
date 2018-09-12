package com.example.melaniez.photoapp.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.melaniez.photoapp.Models.User;
import com.example.melaniez.photoapp.Profile.Settings.EditProfileActivity;
import com.example.melaniez.photoapp.Profile.Settings.SettingsFragment;
import com.example.melaniez.photoapp.R;
import com.example.melaniez.photoapp.Utils.MyImageLoader;
import com.example.melaniez.photoapp.Utils.TabsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements Toolbar.OnMenuItemClickListener {
    private static final String TAG = "ProfileFragment";
    private TabsPagerAdapter mAdapter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private User mUser;

    // TOOLBAR SECTION
    @BindView(R.id.profile_toolbar) Toolbar mToolbar;
    @BindView(R.id.username) TextView mUsername;

    // STATISTICS SECTION
    @BindView(R.id.profile_photo) CircleImageView mProfilePhoto;
    @BindView(R.id.num_posts) TextView mNumPosts;
    @BindView(R.id.num_followers) TextView mNumFollowers;
    @BindView(R.id.num_following) TextView mNumFollowing;
    @OnClick(R.id.edit_profile_button) public void onClickEditProfileButton() {
        startActivity(new Intent(getActivity(), EditProfileActivity.class));
    }

    // DESCRIPTION SECTION
    @BindView(R.id.full_name) TextView mFullName;
    @BindView(R.id.description) TextView mDescription;
    @BindView(R.id.hyperlink) TextView mHyperlink;

    // PHOTO GALLERY SECTION
    @BindView(R.id.profile_tabs) TabLayout mTabLayout;
    @BindView(R.id.profile_view_pager) ViewPager mViewPager;

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    /* ******************************* Fragment Lifecycle Methods *********************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, parent, false);
        ButterKnife.bind(this, view);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot.child(mAuth.getUid());
                mUser = ds.getValue(User.class);
                setupWidgets();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        setupToolBar();
        setupViewPager();
        setupTabs();

        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.ic_add_friend :
                return true;
            case R.id.ic_archive :
                return true;
            case R.id.ic_settings :
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fragment_container, SettingsFragment.newInstance()).addToBackStack(null).commit();
                return true;
            default :
                return false;
        }
    }

    /* ************************************ Helper Methods ****************************************/
    private void setupToolBar() {
        mToolbar.inflateMenu(R.menu.fragment_profile);
        // Comparing to AppCompatActivity.setSupportActionBar(Toolbar toolbar), the method 'inflateMenu(int)' is a better alternative.
        mToolbar.setOnMenuItemClickListener(this);
    }

    private void setupWidgets() {
        mUsername.setText(mUser.getUsername());

        MyImageLoader.setImage(mUser.getProfile_photo(), mProfilePhoto, null, "");
        mNumPosts.setText(String.valueOf(mUser.getPosts()));
        mNumFollowers.setText(String.valueOf(mUser.getFollowers()));
        mNumFollowing.setText(String.valueOf(mUser.getFollowing()));

        mFullName.setText(mUser.getFull_name());
        if (mUser.getProfile_description().isEmpty()) mDescription.setVisibility(View.GONE);
        else {
            mDescription.setVisibility(View.VISIBLE);
            mDescription.setText(mUser.getProfile_description());
        }
        if (mUser.getWebsite().isEmpty()) mHyperlink.setVisibility(View.GONE);
        else {
            mHyperlink.setVisibility(View.VISIBLE);
            mHyperlink.setText(mUser.getWebsite());
        }
    }

    private void setupViewPager() {
        mAdapter = new TabsPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(GridViewFragment.newInstance()); //0.
        mAdapter.addFragment(ListViewFragment.newInstance()); //1.
        mAdapter.addFragment(TaggedPhotosFragment.newInstance()); //2.
        mAdapter.addFragment(ArchiveFragment.newInstance()); //3.
        mViewPager.setAdapter(mAdapter);
    }

    private void setupTabs() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_profile_grid_view);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_profile_list_view);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_profile_tagged_photos);
        mTabLayout.getTabAt(3).setIcon(R.drawable.ic_profile_archive);
    }
}
