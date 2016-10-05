package com.mjm.solid.liquidmanager.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjm.solid.liquidmanager.R;

public class MyRadioButton extends RevealLayoutYellow {

    private ImageView mImageView;
    private View mIndicator;
    private String mDesc;
    private TextView mTvDesc;
    private boolean isCheck;
    private int mImageId;

    public MyRadioButton(Context context) {
        super(context);
        initView(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDesc = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "radioText");
        isCheck = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "radioCheck", false);
        mImageId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "radioImage", 0);
        initView(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.my_radio_button, this);
        mImageView = (ImageView) findViewById(R.id.iv_my_radio_button);
        mIndicator = findViewById(R.id.indicator_my_radio_button);
        mTvDesc = (TextView) findViewById(R.id.tv_my_radio_button);
        mTvDesc.setText(mDesc);
        mImageView.setBackgroundResource(mImageId);
        setChecked(isCheck);
    }


    public void setChecked(boolean checked) {
        isCheck = checked;
        if (checked) {
            mIndicator.setBackgroundResource(R.color.colorAccent);
        } else {
            mIndicator.setBackgroundResource(R.color.colorBg);
        }
    }

}
