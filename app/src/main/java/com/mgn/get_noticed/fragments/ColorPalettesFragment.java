package com.mgn.get_noticed.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.mgn.get_noticed.GetNoticedApplication;
import com.mgn.get_noticed.R;
import com.mgn.get_noticed.adapters.ColorPalettesAdapter;
import com.mgn.get_noticed.util.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class ColorPalettesFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    private static final String[] TITLES = {"Bright Colors (10)", "Rainbow Colors (8)", "All Colors (156)"};
    private static final int[] COLOR_ARRAYS = {R.array.bright_colors, R.array.rainbow_colors, R.array.allColorValues156};


    private ListView mListView;

    public ColorPalettesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_palettes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.colorPalettes_listView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            ColorPalettesAdapter adapter = new ColorPalettesAdapter(TITLES, COLOR_ARRAYS, this);
            mListView.setAdapter(adapter);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Integer position = (Integer) buttonView.getTag();
        if (position != null && isChecked) {
            SharedPreferences.Editor editor = GetNoticedApplication.getInstance().getSharedPreferences().edit();
            editor.putInt(Constants.SELECTED_COLOR_ARRAY, COLOR_ARRAYS[position]);
            editor.apply();
            if (getActivity() != null)
                getActivity().onBackPressed();
        }
    }
}
