package com.mgn.get_noticed.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.mgn.get_noticed.GetNoticedApplication;
import com.mgn.get_noticed.R;
import com.mgn.get_noticed.activities.SettingsActivity;
import com.mgn.get_noticed.adapters.MainSettingsAdapter;
import com.mgn.get_noticed.util.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainSettingsFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static final String[] TITLES = {"Color Palettes", "Display Text"};
    private static final String[] SUBTITLES =
            {"Select from different color palettes to #getNoticed",
                    "Update the display text to #getNoticed",
            };

    private ListView mListView;
    private MainSettingsAdapter mAdapter;


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
            mAdapter = new MainSettingsAdapter(TITLES, SUBTITLES);
            mListView.setAdapter(mAdapter);
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
                break;
            case 1:
                if (activity != null) {
                    showInputDialog(activity);
                }
                break;
        }
    }

    private void showInputDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter Display Text");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setSingleLine();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.default_margin);
        params.setMargins(margin, margin, margin, margin);
        input.setLayoutParams(params);

        FrameLayout container = new FrameLayout(context);
        container.addView(input);
        builder.setView(container);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = GetNoticedApplication.getInstance().getSharedPreferences().edit();
                editor.putString(Constants.DISPLAY_TEXT, input.getText().toString());
                editor.apply();
                mAdapter.notifyDataSetChanged();
            }
        });
        
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
