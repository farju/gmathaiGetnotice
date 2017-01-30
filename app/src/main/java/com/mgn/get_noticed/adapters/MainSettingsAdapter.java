package com.mgn.get_noticed.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mgn.get_noticed.GetNoticedApplication;
import com.mgn.get_noticed.R;
import com.mgn.get_noticed.util.Constants;

public class MainSettingsAdapter extends BaseAdapter {


    private final String[] mTitles;
    private final String[] mSubtitles;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mSubtitleTextView;
        private TextView mValueTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.cell_mainSettings_titleTextView);
            mSubtitleTextView = (TextView) itemView.findViewById(R.id.cell_mainSettings_subtitleTextView);
            mValueTextView = (TextView) itemView.findViewById(R.id.cell_mainSettings_valueTextView);
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
            this.mValueTextView.setVisibility(View.GONE);
        }
    }

    public MainSettingsAdapter(String[] titles, String[] subtitles) {
        mTitles = titles;
        mSubtitles = subtitles;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public String getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cell_main_settings, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.setText(mTitles[position], mSubtitles[position]);

        switch (position) {
            case 1:
                String text = GetNoticedApplication.getInstance().getSharedPreferences().getString(Constants.DISPLAY_TEXT, "");
                text = "Current Text: " + text;
                vh.mValueTextView.setText(text);
                vh.mValueTextView.setVisibility(View.VISIBLE);
                break;
        }

        return convertView;
    }


}
