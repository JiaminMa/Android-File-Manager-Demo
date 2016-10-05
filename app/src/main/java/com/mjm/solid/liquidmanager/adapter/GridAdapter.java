package com.mjm.solid.liquidmanager.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjm.solid.liquidmanager.R;
import com.mjm.solid.liquidmanager.domain.FileInfo;
import com.mjm.solid.liquidmanager.utils.BitmapHelper;
import com.mjm.solid.liquidmanager.utils.UiUtils;

import java.util.List;

public class GridAdapter extends BaseAdapter<GridAdapter.GridViewHolder> {

    private List<Integer> mHeights;

    public GridAdapter(Context context, List<FileInfo> data) {
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

        FileInfo info = mData.get(position);
        if (info.type == FileInfo.TYPE_PIC) {
            holder.mIvThumbNail.setImageBitmap(BitmapHelper.getSmallBitmapFromFile(
                    mData.get(position).path, UiUtils.dip2px(100), UiUtils.dip2px(100)));
        } else if (info.type == FileInfo.TYPE_VIDEO) {
            holder.mIvThumbNail.setImageBitmap(BitmapHelper.getVideoThumbnail(info.path));
        }
        holder.mTvName.setVisibility(View.INVISIBLE);
        holder.mTvPath.setText(mData.get(position).path);
    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        public CardView mRlRoot;
        public ImageView mIvThumbNail;
        public TextView mTvName;
        public TextView mTvPath;
        public GridViewHolder(View itemView) {
            super(itemView);
            mRlRoot = (CardView) itemView.findViewById(R.id.grid_item_cardview);
            mIvThumbNail = (ImageView) itemView.findViewById(R.id.grid_item_thumbnail);
            mTvName = (TextView) itemView.findViewById(R.id.grid_item_name);
            mTvPath = (TextView) itemView.findViewById(R.id.grid_item_path);
        }
    }
}
