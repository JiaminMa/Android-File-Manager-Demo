package com.mjm.solid.liquidmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mjm.solid.liquidmanager.domain.FileInfo;

import java.util.List;

/**
 * Created by mjm on 16-7-16.
 */
public abstract class BaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected Context mContext;
    protected List<FileInfo> mData;
    protected LayoutInflater mInflater;

    public BaseAdapter(Context context, List<FileInfo> data) {
        this.mContext = context;
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(T holder, int position) {
        setContentView(holder, position);
        initEvent(holder, position);
    }

    //Event Related
    protected FileOnClickListener mFileOnClickListener;

    public interface FileOnClickListener {
        void onClick(View v);

        void onLongClick(View v);
    }

    public void setOnItemClickListener(FileOnClickListener listener) {
        this.mFileOnClickListener = listener;
    }

    public void initEvent(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFileOnClickListener != null) {
                    mFileOnClickListener.onClick(v);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if (mFileOnClickListener != null) {
                    FileInfo fileInfo = mData.get(position);
                    mFileOnClickListener.onLongClick(v);
                }
                return true;
            }
        });
    }

    protected abstract void setContentView(T holder, int position);
}
