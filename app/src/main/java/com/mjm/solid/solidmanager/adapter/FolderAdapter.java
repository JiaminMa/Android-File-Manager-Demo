package com.mjm.solid.solidmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mjm.solid.solidmanager.R;
import com.mjm.solid.solidmanager.domain.FileInfo;
import com.mjm.solid.solidmanager.utils.ApkUtils;
import com.mjm.solid.solidmanager.utils.BitmapHelper;
import com.mjm.solid.solidmanager.utils.UiUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FolderAdapter extends BaseAdapter<FolderAdapter.FileViewHolder> {

    public FolderAdapter(Context context, List<FileInfo> data) {
        super(context, data);
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class FileViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvName;
        public ImageView mIvIcon;
        public TextView mTvSize;
        public TextView mTvPermission;
        public TextView mTvPath;
        public LinearLayout mRlRoot;

        public FileViewHolder(View itemView) {
            super(itemView);
            mRlRoot = (LinearLayout) itemView.findViewById(R.id.list_item_root);
            mTvName = (TextView) itemView.findViewById(R.id.list_item_tv_name);
            mIvIcon = (ImageView) itemView.findViewById(R.id.list_item_icon);
            mTvSize = (TextView) itemView.findViewById(R.id.list_item_tv_size);
            mTvPermission = (TextView) itemView.findViewById(R.id.list_item_tv_permission);
            mTvPath = (TextView) itemView.findViewById(R.id.list_item_tv_cannot_visable);
        }
    }

    @Override
    protected void setContentView(FileViewHolder holder, int position) {
        Set<Integer> bigIconList = new HashSet<>();
        if (bigIconList != null && bigIconList.contains(position)) {
            holder.mIvIcon.setPadding(0, 0, 0, 0);
        } else {
            int dip2px = UiUtils.dip2px(8);
            holder.mIvIcon.setPadding(dip2px, dip2px, dip2px, dip2px);
        }
        FileInfo data = mData.get(position);
        holder.mTvName.setText(data.name);
        if (data.isDir) {
            if (data.displayFolderSize) {
                holder.mTvSize.setText(data.fileCount + " 个文件");
            } else {
                holder.mTvSize.setText("目录");
            }
        } else
            holder.mTvSize.setText(data.size);
        if (data.permission.equals(" ")) {
            holder.mTvPermission.setVisibility(View.INVISIBLE);
        } else {
            holder.mTvPermission.setText(data.permission);
            holder.mTvPermission.setVisibility(View.VISIBLE);
        }

        if (data.isDir) {
            holder.mIvIcon.setBackgroundResource(R.drawable.round_blue);
            holder.mIvIcon.setImageResource(R.drawable.ic_folder_white);
        } else {
            if (data.type == FileInfo.TYPE_DOCUMENT) {
                holder.mIvIcon.setBackgroundResource(R.drawable.round_purple);
                holder.mIvIcon.setImageResource(R.drawable.ic_text_white);
            } else if (data.type == FileInfo.TYPE_UNKOWN) {
                holder.mIvIcon.setBackgroundResource(R.drawable.round_gray);
                holder.mIvIcon.setImageResource(R.drawable.ic_shortcut_white);
            } else if (data.type == FileInfo.TYPE_AUDIO) {
                holder.mIvIcon.setBackgroundResource(0);
                holder.mIvIcon.setImageBitmap(BitmapHelper.createAlbumArt(data.path, 40, 40));
                holder.mIvIcon.setPadding(0, 0, 0, 0);
                bigIconList.add(position);
            } else if (data.type == FileInfo.TYPE_VIDEO) {
                holder.mIvIcon.setBackgroundResource(0);
                holder.mIvIcon.setImageBitmap(BitmapHelper.getVideoThumbnail(data.path));
                holder.mIvIcon.setPadding(0, 0, 0, 0);
                bigIconList.add(position);
            } else if (data.type == FileInfo.TYPE_APK) {
                holder.mIvIcon.setBackgroundResource(0);
                holder.mIvIcon.setImageDrawable(ApkUtils.getApkIcon(mContext, data.path));
                holder.mIvIcon.setPadding(0, 0, 0, 0);
                bigIconList.add(position);
            } else if (data.type == FileInfo.TYPE_PIC) {
                holder.mIvIcon.setBackgroundResource(0);
                holder.mIvIcon.setImageBitmap(BitmapHelper.getSmallBitmapFromFile(data.path, 40, 40));
                holder.mIvIcon.setPadding(0, 0, 0, 0);
                bigIconList.add(position);
            }
        }
        holder.mTvPath.setText(data.path);
    }
}
