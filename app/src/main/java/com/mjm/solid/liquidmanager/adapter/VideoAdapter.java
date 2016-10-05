package com.mjm.solid.liquidmanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mjm.solid.liquidmanager.R;
import com.mjm.solid.liquidmanager.domain.FileInfo;
import com.mjm.solid.liquidmanager.utils.BitmapHelper;

import java.util.List;

public class VideoAdapter extends GridAdapter {

    private List<Integer> mHeights;

    public VideoAdapter(Context context, List<FileInfo> data) {
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
        holder.mIvThumbNail.setImageBitmap(BitmapHelper.getVideoThumbnail(mData.get(position).path));
        holder.mTvName.setText(mData.get(position).name);
    }


}
