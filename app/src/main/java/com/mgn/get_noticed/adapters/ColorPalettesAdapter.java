package com.mgn.get_noticed.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mgn.get_noticed.GetNoticedApplication;
import com.mgn.get_noticed.R;
import com.mgn.get_noticed.util.AutoHeightGridView;
import com.mgn.get_noticed.util.Constants;

public class ColorPalettesAdapter extends BaseAdapter {

    private final int[] mColorArrays;
    private String[] mTitles;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckBox;
        private TextView mTitleTextView;
        private AutoHeightGridView mGridView;

        private ViewHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.cell_colorPalette_checkBox);
            mGridView = (AutoHeightGridView) itemView.findViewById(R.id.cell_colorPalette_gridView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.cell_colorPalette_titleTextView);
        }

        private void setText(String title, int[] colors) {
            mTitleTextView.setText(title);
            mGridView.setAdapter(new MiniColorCellAdapter(colors));
        }

        private void setSelected(boolean isSelected) {
            mCheckBox.setChecked(isSelected);
        }


        private void setCheckBox(CompoundButton.OnCheckedChangeListener onCheckedChangeListener, int position) {
            mCheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
            mCheckBox.setTag(position);
        }
    }

    public ColorPalettesAdapter(String[] titles, int[] colorArrays, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        mTitles = titles;
        mColorArrays = colorArrays;
        mOnCheckedChangeListener = onCheckedChangeListener;
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
                    .inflate(R.layout.cell_color_palette, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setText(mTitles[position],
                parent.getContext().getResources().getIntArray(mColorArrays[position]));
        vh.setSelected(GetNoticedApplication.getInstance().getSharedPreferences().getInt(Constants.SELECTED_COLOR_ARRAY, -100) == mColorArrays[position]);
        vh.setCheckBox(mOnCheckedChangeListener, position);
        return convertView;
    }


}
