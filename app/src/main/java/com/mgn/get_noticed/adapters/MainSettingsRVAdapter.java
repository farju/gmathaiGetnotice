package com.mgn.get_noticed.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mgn.get_noticed.R;

/**
 * Created by a6001899 on 1/24/17.
 */

public class MainSettingsRVAdapter extends RecyclerView.Adapter<MainSettingsRVAdapter.ViewHolder> {


    private final String[] mTitles;
    private final String[] mSubtitles;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mSubtitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.cell_mainSettings_titleTextView);
            mSubtitleTextView = (TextView) itemView.findViewById(R.id.cell_mainSettings_subtitleTextView);
        }

//        public void setTitle(String text) {
//            this.mTitleTextView.setText(text);
//        }
//
//        public void setSubtitle(String text) {
//            this.mSubtitleTextView.setText(text);
//        }

        public void setText(String title, String subtitle) {
            this.mTitleTextView.setText(title);
            this.mSubtitleTextView.setText(subtitle);
        }
    }

    public MainSettingsRVAdapter(String[] titles, String[] subtitles) {
        mTitles = titles;
        mSubtitles = subtitles;
    }


    @Override
    public MainSettingsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_main_settings, parent, false));
    }

    @Override
    public void onBindViewHolder(MainSettingsRVAdapter.ViewHolder holder, int position) {
        holder.setText(mTitles[position], mSubtitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }
}
