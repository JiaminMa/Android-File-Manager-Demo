package com.mjm.solid.solidmanager;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mjm.solid.solidmanager.domain.FileInfo;
import com.mjm.solid.solidmanager.pager.BasePager;
import com.mjm.solid.solidmanager.pager.ContentPager;
import com.mjm.solid.solidmanager.pager.InfoPager;
import com.mjm.solid.solidmanager.pager.SizePager;
import com.mjm.solid.solidmanager.utils.BitmapHelper;
import com.mjm.solid.solidmanager.utils.FileInfoUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CharacterActivity extends AppCompatActivity {

    private ViewPager mVpCharacter;
    private List<BasePager> mPagerList;
    private CharacterAdapter mAdapter;
    private String[] mTabIndex = new String[]{"文件夹信息", "内容", "前20"};
    private PagerTabStrip mStripHome;
    private RelativeLayout mRlChar;
    private String mPath;
    public static final String FILE_PATH = "file_path";
    private RoundRobinPicTask mRoundRobinPicTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_character);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_character);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mPath = intent.getStringExtra(FILE_PATH);
        List<FileInfo> fileInfoList = FileInfoUtils.getFileInfoFromPath(mPath, this);
        mVpCharacter = (ViewPager) findViewById(R.id.character_view_pager);
        mStripHome = (PagerTabStrip) findViewById(R.id.pager_tab_strip_home);
        mRlChar = (RelativeLayout) findViewById(R.id.rl_character);

        mPagerList = new ArrayList<>();
        mPagerList.add(new InfoPager(this, mPath, fileInfoList));
        mPagerList.add(new ContentPager(this, mPath, fileInfoList));
        mPagerList.add(new SizePager(this, mPath, fileInfoList));

        mAdapter = new CharacterAdapter();
        mVpCharacter.setAdapter(mAdapter);
        mStripHome.setTabIndicatorColor(getResources().getColor(R.color.colorPrimary));

        mRoundRobinPicTask = new RoundRobinPicTask();
//        mRoundRobinPicTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRoundRobinPicTask.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.character, menu);
        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class CharacterAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabIndex[position];
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerList.get(position);
            pager.initData();
            container.addView(pager.mRootView);
            return pager.mRootView;
        }
    }

    class RoundRobinPicTask extends AsyncTask<Void, Integer, Void> {

        private boolean isRun = true;
        private List<FileInfo> mPicList;

        @Override
        protected Void doInBackground(Void... params) {
            mPicList = FileInfoUtils.getPicUnderFolder(CharacterActivity.this, mPath);
            int index = 0;
            while (isRun) {
                publishProgress(index);
                SystemClock.sleep(5000);
                index++;
                if (index >= mPicList.size())
                    index = 0;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            FileInfo info = mPicList.get(values[0]);
            mRlChar.setBackground(new BitmapDrawable(BitmapHelper.getSmallBitmapFromFile(info.path, 100, 100)));
        }

        public void stop() {
            isRun = false;
        }
    }
}
