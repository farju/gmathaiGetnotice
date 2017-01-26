package com.mgn.get_noticed.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mgn.get_noticed.R;

public class MainSettingsAdapter extends BaseAdapter {


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
        return convertView;
    }


}
