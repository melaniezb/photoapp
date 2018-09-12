package com.example.melaniez.photoapp.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.melaniez.photoapp.R;
import com.example.melaniez.photoapp.Utils.GridViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridViewFragment extends Fragment {
    private Context mContext;
    private ArrayList<String> mSampleImages = new ArrayList<>();
    @BindView(R.id.profile_grid_view) GridView mGridView;

    public static GridViewFragment newInstance() {
        Bundle args = new Bundle();
        GridViewFragment fragment = new GridViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mSampleImages.add("http://coolestone.com/thumbs/011c2179e13d.jpg");
        mSampleImages.add("https://images-cdn.9gag.com/photo/aDW7w2B_700b.jpg");
        mSampleImages.add("https://78.media.tumblr.com/93d2c4a820b3a92ac86a863d7773b68d/tumblr_n8opabICI21rq0q4yo3_500.jpg");
        mSampleImages.add("https://2static.fjcdn.com/pictures/Cockatiel+dodo_9e337a_6020029.jpg");
        mSampleImages.add("https://i.pinimg.com/236x/d2/78/49/d278493468165edc3aaac0d8960ec9da.jpg");
        mSampleImages.add("https://i.pinimg.com/236x/40/e9/53/40e953773a2f6757ebbe90dc12229aa3--lol-funny-funny-jokes.jpg");
        mSampleImages.add("https://78.media.tumblr.com/2c44d8919322080768dc0fde985e2222/tumblr_o4xnbnfVbm1s5isyuo1_1280.jpg");
        mSampleImages.add("https://dailylolpics.com/wp-content/uploads/2017/09/image-763.jpeg");
        mSampleImages.add("https://i.pinimg.com/originals/e5/e5/d7/e5e5d77471b8779b3c47fc56eee4382a.jpg");
        mSampleImages.add("https://i.pinimg.com/originals/e4/86/89/e486894d2382ea14d6bf150626434394.jpg");
        mSampleImages.add("https://i.pinimg.com/originals/40/e3/32/40e332dd0a07f6a998430e2afe4558dd.jpg");
        mSampleImages.add("https://pics.esmemes.com/what-if-i-told-u-r-accualy-trin-2-take-14425205.png");
        mSampleImages.add("https://pics.me.me/funny-jean-claude-van-cockatiel-16693128.png");
        mSampleImages.add("https://shirleytwofeathers.com/The_Blog/memecity/wp-content/uploads/sites/15/2016/11/funny-pictures-birds-not-speaking-parrots-475x297.jpg");
        mSampleImages.add("https://pbs.twimg.com/media/DU2nbX4VMAA_pau.jpg");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid_view, container, false);
        ButterKnife.bind(this, view);

        mGridView.setAdapter(new GridViewAdapter(mContext, mSampleImages));
        return view;
    }

    public void setGridViewDimension() {
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/3;
        mGridView.setColumnWidth(imageWidth);

    }
}
