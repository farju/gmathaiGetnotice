package com.mgn.get_noticed;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgn.get_noticed.adapters.MainSettingsRVAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainSettingsFragment extends Fragment {

    private static final String[] TITLES = {"Color Palettes"};
    private static final String[] SUBTITLES = {"Select from different color palettes to show on the main screen"};


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mainSettings_recyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(activity);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new MainSettingsRVAdapter(TITLES, SUBTITLES);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
