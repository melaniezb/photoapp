package com.example.melaniez.photoapp.Profile.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.melaniez.photoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.MeasureSpec.UNSPECIFIED;

public class SettingsFragment extends Fragment {
    Context mContext;
    private static final String TAG = "SettingsFragment";
    @BindView(R.id.settings_listview_people) ListView mFollowPeopleList;
    @BindView(R.id.settings_listview_account) ListView mAccountList;
    @BindView(R.id.settings_listview_privacy) ListView mPrivacyList;
    @BindView(R.id.settings_listview_notifications) ListView mNotificationList;
    @BindView(R.id.settings_listview_about) ListView mAboutList;
    @BindView(R.id.settings_listview_logins) ListView mLoginsList;
    @OnClick(R.id.profile_settings_back_arrow) public void goBack() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        setUpListViews();
        return  view;
    }

    /* ************************************ Helper Methods ****************************************/

    /**
     *
     * @param listView
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0 ; i < listView.getCount() ; i++) {
            view = adapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(desiredWidth, UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getCount() - 1) * listView.getDividerHeight();
    }

    /**
     *
     */
    private void setUpListViews() {

        // Follow People listView
        String[] optionsPeople = {"Facebook Friends", "Contacts"};
        mFollowPeopleList.setAdapter(new ChevronViewAdapter(optionsPeople));
        setListViewHeightBasedOnChildren(mFollowPeopleList);

        // Account listView
        String[] optionsAccount = {
                "Password", "Muted accounts", "Payments",
                "Posts you've liked", "Search history",
                "Mobile data use", "Language", "Switch to Business Profile"
        };
        mAccountList.setAdapter(new ChevronViewAdapter(optionsAccount));
        setListViewHeightBasedOnChildren(mAccountList);

        // Privacy and Security
        String[] optionsPrivacy = {
                "Blocked accounts", "Activity status", "Saved login information",
                "Story controls", "Comment controls", "Photos of you",
                "Linked accounts", "Account data", "Two-factor authentication"
        };
        mPrivacyList.setAdapter(new ChevronViewAdapter(optionsPrivacy));
        setListViewHeightBasedOnChildren(mPrivacyList);

        // Notification
        String[] optionsNotification = {"Push notifications", "Email and SMS notifications"};
        mNotificationList.setAdapter(new ChevronViewAdapter(optionsNotification));
        setListViewHeightBasedOnChildren(mNotificationList);

        // About
        String[] optionsAbout = {"Ads", "Data Policy", "Open-source libraries", "Terms"};
        mAboutList.setAdapter(new ChevronViewAdapter(optionsAbout));
        setListViewHeightBasedOnChildren(mAboutList);

        // Logins
        String[] optionsLogins = {"Add Account", "Log out of jingjing_zong"};
        mLoginsList.setAdapter(new BlueFontViewAdapter(optionsLogins));
        mLoginsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    LogOutDialog dialog = new LogOutDialog();
                    dialog.show(getFragmentManager(), "LogOUtDialog");
                }
            }
        });
        setListViewHeightBasedOnChildren(mLoginsList);
    }

    /* ************************************* Inner Classes ****************************************/

    /**
     *
     */
    private class ChevronViewAdapter extends ArrayAdapter {
        private String[] mOptions;

        ChevronViewAdapter(String[] options){
            super(mContext, R.layout.chevron_view, options);
            mOptions = options;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.chevron_view, parent, false);
            TextView textView = view.findViewById(R.id.chevron_view_text);
            textView.setText(mOptions[position]);
            return view;
        }
    }

    /**
     *
     */
    private class BlueFontViewAdapter extends ArrayAdapter<String> { // <String> ensures that the method 'getItem()' will return a string
        private LayoutInflater mInflater;

        BlueFontViewAdapter(String[] options){
            super(mContext, R.layout.blue_font_view, options);
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.blue_font_view, parent, false);
                //View view = getLayoutInflater().inflate(R.layout.blue_font_view, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mTextView = convertView.findViewById(R.id.blue_font_view_text);
                //TextView textView = view.findViewById(R.id.blue_font_view_text);
                convertView.setTag(viewHolder);
            } else viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.mTextView.setText(getItem(position));
            //textView.setText(mOptions[position]);

            return convertView;
            //return view;
        }

        private class ViewHolder {
            TextView mTextView;
        }
    }
}
