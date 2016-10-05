package com.mjm.solid.liquidmanager.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.mjm.solid.liquidmanager.R;
import com.mjm.solid.liquidmanager.adapter.BaseAdapter;
import com.mjm.solid.liquidmanager.adapter.PhotoAdapter;
import com.mjm.solid.liquidmanager.domain.FileInfo;
import com.mjm.solid.liquidmanager.domain.ImageInfo;
import com.mjm.solid.liquidmanager.engine.ImageProvider;
import com.mjm.solid.liquidmanager.engine.MediaProviderFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PhotoFragment extends GridFragment {

    private Map<String, List<ImageInfo>> mImageMap;
    @Override
    protected void onItemClick(View v) {
        TextView textView = (TextView) v.findViewById(R.id.grid_item_name);
        String key = textView.getText().toString();
        List<ImageInfo> imageInfos = mImageMap.get(key);
        if (imageInfos != null &&  imageInfos.size() > 0) {
            String cmplPath = imageInfos.get(0).path;
            String[] path = cmplPath.split(imageInfos.get(0).displayName);
            FragmentStackManager.getInstance().switchGridFragment(path[0], path[0]);
        }
    }

    @Override
    protected void onItemLongClick(View v) {
    }

    @Override
    protected BaseAdapter newAdapter(Activity activity, List<FileInfo> fileInfo) {
        return new PhotoAdapter(activity, fileInfo);
    }

    public PhotoFragment(String mLabel) {
        super(mLabel);
    }

    @Override
    protected List<FileInfo> getData() {
        List<FileInfo> list = new ArrayList<>();

        MediaProviderFactory<ImageInfo> mediaProvider = new ImageProvider(mActivity);
        List<ImageInfo> imgInfos = mediaProvider.getList();
        //把图片分组
        long start = System.currentTimeMillis();
        mImageMap = new HashMap();
        for (ImageInfo info : imgInfos) {
            String key = info.bucketName;
            List<ImageInfo> tmp = mImageMap.get(key);
            if (tmp == null) {
                tmp = new ArrayList();
            }
            tmp.add(info);
            mImageMap.put(key, tmp);
        }

        Iterator it = mImageMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<ImageInfo>> entry = (Map.Entry<String, List<ImageInfo>>) it.next();
            String key = entry.getKey();
            List<ImageInfo> val = entry.getValue();
            FileInfo fileInfo = new FileInfo();
            fileInfo.name = key;
            fileInfo.path = val.get(0).path;
            list.add(fileInfo);
        }
        return list;
    }

    public PhotoFragment() {
        super();

    }

    @Override
    public void refreshData() {

    }
}
