package com.mgn.get_noticed.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mgn.get_noticed.R;

class MiniColorCellAdapter extends BaseAdapter {

    private final int[] mColors;

    MiniColorCellAdapter(int[] colors) {
        this.mColors = colors;
    }

    @Override
    public int getCount() {
        return mColors.length;
    }

    @Override
    public Object getItem(int position) {
        return mColors[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cell_color, parent, false);
        }
        convertView.setBackgroundColor(mColors[position]);
        return convertView;
    }
}
