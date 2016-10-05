package com.mjm.solid.solidmanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mjm.solid.solidmanager.R;
import com.mjm.solid.solidmanager.domain.FileInfo;
import com.mjm.solid.solidmanager.utils.BitmapHelper;
import com.mjm.solid.solidmanager.utils.UiUtils;

import java.util.List;

public class PhotoAdapter extends GridAdapter {

    private List<Integer> mHeights;

    public PhotoAdapter(Context context, List<FileInfo> data) {
        super(context, data);
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_item, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    protected void setContentView(GridViewHolder holder, int position) {
        holder.mIvThumbNail.setImageBitmap(BitmapHelper.getSmallBitmapFromFile(
                mData.get(position).path, UiUtils.dip2px(100), UiUtils.dip2px(100)));
        holder.mTvName.setText(mData.get(position).name);
    }

}
