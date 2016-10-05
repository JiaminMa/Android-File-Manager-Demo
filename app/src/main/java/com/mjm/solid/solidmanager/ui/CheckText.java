package com.mjm.solid.solidmanager.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mjm.solid.solidmanager.R;

public class CheckText extends RevealLayout {

    private TextView mTvOption;
    private CheckBox mCbOption;
    private String mOption;

    public CheckText(Context context) {
        super(context);
        initView(context);
    }

    public CheckText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOption = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "folderoption");
        initView(context);
    }

    public CheckText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        View.inflate(context, R.layout.checktext_layout, this);
        mTvOption = (TextView) findViewById(R.id.tv_check_text);
        mCbOption = (CheckBox) findViewById(R.id.cb_check_text);
        mTvOption.setText(mOption);
    }

    public void setText(String text) {
        mTvOption.setText(text);
    }

    public void setChecked(boolean checked) {
        mCbOption.setChecked(checked);
    }

    public boolean isChecked() {
        return mCbOption.isChecked();
    }
}
