package com.mjm.solid.solidmanager.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.mjm.solid.solidmanager.R;
import com.mjm.solid.solidmanager.adapter.BaseAdapter;
import com.mjm.solid.solidmanager.adapter.VideoAdapter;
import com.mjm.solid.solidmanager.domain.FileInfo;
import com.mjm.solid.solidmanager.domain.VideoInfo;
import com.mjm.solid.solidmanager.engine.MediaProviderFactory;
import com.mjm.solid.solidmanager.engine.VideoProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VideoFragment extends GridFragment {

    public VideoFragment(String mLabel) {
        super(mLabel);
    }

    @Override
    public void refreshData() {

    }

    private Map<String, List<VideoInfo>> mVideoMap;

    @Override
    protected List<FileInfo> getData() {
        List<FileInfo> list = new ArrayList<>();

        MediaProviderFactory<VideoInfo> mediaProvider = new VideoProvider(mActivity);
        List<VideoInfo> videoInfos = mediaProvider.getList();
        //把视频分组
        long start = System.currentTimeMillis();
        mVideoMap = new HashMap();
        for (VideoInfo info : videoInfos) {
            String key = info.bucketName;
            List<VideoInfo> tmp = mVideoMap.get(key);
            if (tmp == null) {
                tmp = new ArrayList();
            }
            tmp.add(info);
            mVideoMap.put(key, tmp);
        }

        Iterator it = mVideoMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<VideoInfo>> entry = (Map.Entry<String, List<VideoInfo>>) it.next();
            String key = entry.getKey();
            List<VideoInfo> val = entry.getValue();
            FileInfo fileInfo = new FileInfo();
            fileInfo.name = key;
            fileInfo.path = val.get(0).path;
            list.add(fileInfo);
        }
        return list;
    }

    @Override
    protected BaseAdapter newAdapter(Activity activity, List<FileInfo> fileInfo) {
        return new VideoAdapter(activity, fileInfo);
    }

    @Override
    protected void onItemClick(View v) {
        TextView textView = (TextView) v.findViewById(R.id.grid_item_name);
        String key = textView.getText().toString();
        List<VideoInfo> imageInfos = mVideoMap.get(key);
        if (imageInfos != null &&  imageInfos.size() > 0) {
            String cmplPath = imageInfos.get(0).path;
            String[] path = cmplPath.split(imageInfos.get(0).displayName);
            FragmentStackManager.getInstance().switchGridFragment(path[0], path[0]);
        }
    }
}
