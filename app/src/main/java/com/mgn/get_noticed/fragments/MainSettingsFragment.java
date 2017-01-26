package com.mgn.get_noticed.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mgn.get_noticed.R;
import com.mgn.get_noticed.activities.SettingsActivity;
import com.mgn.get_noticed.adapters.MainSettingsAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainSettingsFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static final String[] TITLES = {"Color Palettes"};
    private static final String[] SUBTITLES = {"Select from different color palettes to show on the main screen"};

    private ListView mListView;

    public MainSettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.mainSettings_listView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            MainSettingsAdapter adapter = new MainSettingsAdapter(TITLES, SUBTITLES);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SettingsActivity activity = (SettingsActivity) getActivity();
        switch (position) {
            case 0:
                if (activity != null) {
                    activity.replaceFragment(new ColorPalettesFragment());
                }
        }
    }
}
