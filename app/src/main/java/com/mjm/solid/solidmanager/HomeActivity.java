package com.mjm.solid.solidmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mjm.solid.solidmanager.fragment.DirFragment;
import com.mjm.solid.solidmanager.fragment.FragmentStackManager;
import com.mjm.solid.solidmanager.fragment.GridFragment;
import com.mjm.solid.solidmanager.fragment.ListFragment;
import com.mjm.solid.solidmanager.global.FilePath;
import com.mjm.solid.solidmanager.ui.CheckText;
import com.mjm.solid.solidmanager.ui.MyRadioButton;
import com.mjm.solid.solidmanager.utils.PreUtils;
import com.mjm.solid.solidmanager.utils.FileOperationManager;
import com.mjm.solid.solidmanager.utils.UiUtils;

import java.lang.reflect.Method;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddFile(view);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        initView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        //Find the menu item in menu
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //Set the open and close listener
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(HomeActivity.this, "onExpand", Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(HomeActivity.this, "Collapse", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.action_folder_option:
                changeFolderOption(item);
                break;
            case R.id.action_operation:
                intent = new Intent(HomeActivity.this, OperationActivity.class);
                startActivity(intent);
                break;
            case R.id.action_character:
                intent = new Intent(HomeActivity.this, CharacterActivity.class);
                intent.putExtra(CharacterActivity.FILE_PATH, mFragStackManager.getPeek());
                startActivity(intent);
                break;
            case R.id.action_view_option:
                changeViewOption(item);
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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

    ;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_root:
                mFragStackManager.switchFragment(FilePath.ROOT_DIR, FilePath.ROOT_DIR);
                mTvFolder.setText(FilePath.ROOT_DIR);
                break;
            case R.id.nav_internal:
                mFragStackManager.switchFragment(FilePath.SDCARD_DIR, FilePath.SDCARD_DIR);
                mTvFolder.setText(FilePath.SDCARD_DIR);
                break;
            case R.id.nav_photo:
                mFragStackManager.switchFragment(GridFragment.PHOTO_FRAGMENT_LABEL,
                        GridFragment.PHOTO_FRAGMENT_LABEL);
                break;
            case R.id.nav_audio:
                mFragStackManager.switchFragment(GridFragment.AUDIO_FRAGMENT_LABEL,
                        GridFragment.AUDIO_FRAGMENT_LABEL);
                break;
            case R.id.nav_video:
                mFragStackManager.switchFragment(GridFragment.VIDEO_FRAGMENT_LABEL,
                        GridFragment.VIDEO_FRAGMENT_LABEL);
                break;
            case R.id.nav_app:
                mFragStackManager.switchFragment(GridFragment.APP_FRAGMENT_LABEL,
                        GridFragment.APP_FRAGMENT_LABEL);
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //MJm Created
    private FrameLayout mFlHome;
    private TextView mTvFolder;

    private FragmentManager mFragmentManager;
    private FragmentStackManager mFragStackManager;
    private ImageView mIvAccept;
    private ImageView mIvCancel;

    public static final String PREF_DISPLAY_FOLDER_SIZE = "pref_display_folder_size";
    public static final String PREF_FOLDER_PRE = "pref_folder_pre";
    public static final String PREF_DISPLAY_HIDDEN = "pref_display_hidden";
    public static final String PREF_DISPLAY_MODE = "pref_display_mode";

    public static final int DISPLAY_AS_LIST = 0;
    public static final int DISPLAY_AS_GRID = 1;
    public static final int DISPLAY_AS_GALLERY = 2;

    public static final String SWITCH_FRAGMENT_ACTION = "android.intent.action.switch_fragment";
    public static final String SWITCH_FOLDER_PATH = "switch_folder_path";

    public static final String COPY_CHANGE_ACTION = "android.intent.action.home.copy.change";
    public static final String COPY_CONFIRM_ACTION = "android.intent.action.home.copy.cancel";
    private HomeViewChangeReceiver mHomeViewChangeReceiver;

    private long mExitTime = 0;

    private void initView() {

        mFlHome = (FrameLayout) findViewById(R.id.fl_home);
        mTvFolder = (TextView) findViewById(R.id.tv_home_title);
        mIvAccept = (ImageView) findViewById(R.id.home_iv_accept);
        mIvCancel = (ImageView) findViewById(R.id.home_iv_cancel);

        View headerView = mNavigationView.getHeaderView(0);
        ImageView ivSettings = (ImageView) headerView.findViewById(R.id.nav_settings);
        ImageView ivOption = (ImageView) headerView.findViewById(R.id.nav_option);

        mFragmentManager = getSupportFragmentManager();
        mFragStackManager = FragmentStackManager.getInstance(mFragmentManager, this);
        mFragStackManager.setTextPath(mTvFolder);
        mFragStackManager.switchFragment(FilePath.ROOT_DIR, FilePath.ROOT_DIR);

        mHomeViewChangeReceiver = new HomeViewChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(COPY_CHANGE_ACTION);
        filter.addAction(COPY_CONFIRM_ACTION);
        registerReceiver(mHomeViewChangeReceiver, filter);

        mIvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curPath = mTvFolder.getText().toString();
                FileOperationManager.getInstance().confirm(curPath);
                Intent intent = new Intent(COPY_CONFIRM_ACTION);
                sendBroadcast(intent);
            }
        });

        mIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(COPY_CONFIRM_ACTION);
                sendBroadcast(intent);
            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ivOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNavOption(v);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragStackManager.clear();
        FileOperationManager.getInstance().clear();
        unregisterReceiver(mHomeViewChangeReceiver);
    }


    private void changeFolderOption(MenuItem item) {

        //A custom layout to display the folder option
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_app_item, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View toobar = findViewById(R.id.toolbar);
        popupWindow.showAtLocation(toobar, Gravity.RIGHT | Gravity.TOP, UiUtils.dip2px(10), UiUtils.dip2px(40));

        final CheckText ctDisplayHide = (CheckText) contentView.findViewById(R.id.ct_display);
        final CheckText ctFolderPre = (CheckText) contentView.findViewById(R.id.ct_text_pre);
        CheckText ctMemory = (CheckText) contentView.findViewById(R.id.ct_memory);
        final CheckText ctFolderSize = (CheckText) contentView.findViewById(R.id.ct_folder_size);
        CheckText ctGroup = (CheckText) contentView.findViewById(R.id.ct_group);

        ctFolderSize.setChecked(PreUtils.getBoolean(HomeActivity.this, PREF_DISPLAY_FOLDER_SIZE, false));
        ctFolderPre.setChecked(PreUtils.getBoolean(HomeActivity.this, PREF_FOLDER_PRE, false));
        ctDisplayHide.setChecked(PreUtils.getBoolean(HomeActivity.this, PREF_DISPLAY_HIDDEN, false));

        ctFolderSize.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean isChecked = ctFolderSize.isChecked();
                ctFolderSize.setChecked(!isChecked);
                PreUtils.setBoolean(HomeActivity.this, PREF_DISPLAY_FOLDER_SIZE, !isChecked);
                sendListChangeBroadcast(ListFragment.DISPLAY_FOLDER_SIZE, popupWindow, isChecked);
            }
        });

        ctFolderPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ctFolderPre.isChecked();
                ctFolderPre.setChecked(!isChecked);
                PreUtils.setBoolean(HomeActivity.this, PREF_FOLDER_PRE, !isChecked);
                sendListChangeBroadcast(ListFragment.FOLDER_PRE, popupWindow, isChecked);
            }
        });

        ctDisplayHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ctDisplayHide.isChecked();
                ctDisplayHide.setChecked(!isChecked);
                PreUtils.setBoolean(HomeActivity.this, PREF_DISPLAY_HIDDEN, !isChecked);
                sendListChangeBroadcast(ListFragment.DISPLAY_HIDE, popupWindow, isChecked);
            }
        });

    }

    private void changeViewOption(MenuItem item) {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_view_mode, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View toobar = findViewById(R.id.toolbar);
        popupWindow.showAtLocation(toobar, Gravity.RIGHT | Gravity.TOP, UiUtils.dip2px(10), UiUtils.dip2px(40));

        final MyRadioButton mRbList = (MyRadioButton) contentView.findViewById(R.id.home_rb_list);
        final MyRadioButton mRbGrid = (MyRadioButton) contentView.findViewById(R.id.home_rb_grid);
        final MyRadioButton mRbGallery = (MyRadioButton) contentView.findViewById(R.id.home_rb_gallery);

        final int displayMode = PreUtils.getInt(HomeActivity.this, PREF_DISPLAY_MODE, DISPLAY_AS_LIST);
        switch (displayMode) {
            case DISPLAY_AS_LIST:
                mRbList.setChecked(true);
                mRbGallery.setChecked(false);
                mRbGrid.setChecked(false);
                break;
            case DISPLAY_AS_GALLERY:
                mRbList.setChecked(false);
                mRbGallery.setChecked(true);
                mRbGrid.setChecked(false);
                break;
            case DISPLAY_AS_GRID:
                mRbList.setChecked(false);
                mRbGallery.setChecked(false);
                mRbGrid.setChecked(true);
                break;
        }

        final Intent intent = new Intent(DirFragment.LIST_VIEW_CHANEGE_ACTION);
        mRbList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRbList.setChecked(true);
                mRbGallery.setChecked(false);
                mRbGrid.setChecked(false);
                PreUtils.setInt(HomeActivity.this, PREF_DISPLAY_MODE, DISPLAY_AS_LIST);
                intent.putExtra(DirFragment.LIST_VIEW_MODE, DISPLAY_AS_LIST);
                sendBroadcast(intent);
                popupWindow.dismiss();

            }
        });

        mRbGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRbList.setChecked(false);
                mRbGallery.setChecked(true);
                mRbGrid.setChecked(false);
                PreUtils.setInt(HomeActivity.this, PREF_DISPLAY_MODE, DISPLAY_AS_GALLERY);
                intent.putExtra(DirFragment.LIST_VIEW_MODE, DISPLAY_AS_GALLERY);
                sendBroadcast(intent);
                popupWindow.dismiss();
            }
        });

        mRbGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRbList.setChecked(false);
                mRbGallery.setChecked(false);
                mRbGrid.setChecked(true);
                PreUtils.setInt(HomeActivity.this, PREF_DISPLAY_MODE, DISPLAY_AS_GRID);
                intent.putExtra(DirFragment.LIST_VIEW_MODE, DISPLAY_AS_GRID);
                sendBroadcast(intent);
                popupWindow.dismiss();
            }
        });
    }

    private void sendListChangeBroadcast(int listChangeType, PopupWindow popupWindow, boolean isChecked) {
        Intent intent = new Intent(ListFragment.LIST_CHANGE_ACTION);
        intent.putExtra(ListFragment.LIST_CHANGE_TYPE, listChangeType);
        intent.putExtra(ListFragment.LIST_CHANG_ISCHECKED, !isChecked);
        sendBroadcast(intent);
        popupWindow.dismiss();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            String peek = mFragStackManager.getPeek();
            if (peek.equals(FilePath.ROOT_DIR)) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    super.onBackPressed();
                }
            } else {
                mFragStackManager.removeTop();
            }
        }
    }

    class HomeViewChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(COPY_CHANGE_ACTION)) {
                mIvCancel.setVisibility(View.VISIBLE);
                mIvAccept.setVisibility(View.VISIBLE);
            } else if (action.equals(COPY_CONFIRM_ACTION)) {
                mIvCancel.setVisibility(View.INVISIBLE);
                mIvAccept.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void handleNavOption(View v) {

        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_nav_options, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int[] location = new int[2];
        v.getLocationInWindow(location);
        int dip = 60;
        int px = UiUtils.dip2px(120);

        popupWindow.showAtLocation(v, Gravity.LEFT | Gravity.TOP,
                px, location[1]);

    }

    private void handleAddFile(final View v) {
        final View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_add_file, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final int[] location = new int[2];
        View toobar = findViewById(R.id.toolbar);
        v.getLocationInWindow(location);
        int dip = 60;
        final int px = UiUtils.dip2px(160);

        backgroundAlpha(0.5f);

        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.update();
        popupWindow.showAtLocation(v, Gravity.LEFT | Gravity.TOP,
                px, location[1] - 430);

        // 相对于自己
        int pivotType = Animation.RELATIVE_TO_SELF;
        // 取自身区域在X轴上的中心点
        float pivotX = .5f;
        // 取自身区域在Y轴上的中心点
        float pivotY = .5f;
        RotateAnimation ra = new RotateAnimation(0f, 45f, pivotType, pivotX, pivotType, pivotY);
        ra.setDuration(300);
        ra.setFillAfter(true);
        v.startAnimation(ra);

        ImageView addFolder = (ImageView) contentView.findViewById(R.id.iv_add_folder);
        ImageView addFile = (ImageView) contentView.findViewById(R.id.iv_add_file);
        ImageView addCloud = (ImageView) contentView.findViewById(R.id.iv_add_cloud);

        addFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "新建文件夹", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        addFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "新建文件", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        addCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "新建云链接", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                int pivotType = Animation.RELATIVE_TO_SELF;
                float pivotX = .5f;
                float pivotY = .5f;
                RotateAnimation ra = new RotateAnimation(45f, 0f, pivotType, pivotX, pivotType, pivotY);
                ra.setDuration(300);
                ra.setFillAfter(true);
                v.startAnimation(ra);
                backgroundAlpha(1f);
            }
        });
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }
}
