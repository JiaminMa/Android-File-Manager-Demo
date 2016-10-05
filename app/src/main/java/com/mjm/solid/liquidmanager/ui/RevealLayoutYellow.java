package com.mjm.solid.liquidmanager.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.mjm.solid.liquidmanager.R;

import java.util.ArrayList;

/**
 * Created by mjm on 16-7-15.
 */
public class RevealLayoutYellow extends LinearLayout implements Runnable
{

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 被点击的控件的宽高
    private int mTargetWidth;
    private int mTargetHeight;
    // 在被选中的控件长宽中的最大值和最小值
    private int mMinBetweenWidthAndHeight;
    private int mMaxBetweenWidthAndHeight;

    // mMaxRadius为绘制的水波纹圆圈最大的半径
    private int mMaxRevealRadius;
    // mRevealRadiusGap为每次重新绘制半径增加的值
    private int mRevealRadiusGap;
    // mRevealRadius为初始的数值
    private int mRevealRadius = 0;
    // 用户点击处的坐标
    private float mCenterX;
    private float mCenterY;
    // 获取自定义控件MyRevealLayout 在屏幕上的位置
    private int[] mLocationInScreen = new int[2];
    // 是否执行动画
    private boolean mShouldDoAnimation = false;
    // 是否被按下
    private boolean mIsPressed = false;
    // 重新绘制的时间 单位毫秒
    private int INVALIDATE_DURATION = 20;
    // mTouchTarget指的是用户点击的那个view
    private View mTouchTarget;
    // 松手的事件分发线程
    private DispatchUpTouchEventRunnable mDispatchUpTouchEventRunnable = new DispatchUpTouchEventRunnable();

    public RevealLayoutYellow(Context context)
    {
        super(context);
        init();
    }

    public RevealLayoutYellow(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RevealLayoutYellow(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        setWillNotDraw(false);
        mPaint.setColor(getResources().getColor(R.color.colorAccentTransparent));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        this.getLocationOnScreen(mLocationInScreen);
    }

    private void initParametersForChild(MotionEvent event, View view)
    {
        mCenterX = event.getX();
        mCenterY = event.getY();
        mTargetWidth = view.getMeasuredWidth();
        mTargetHeight = view.getMeasuredHeight();
        mMinBetweenWidthAndHeight = Math.min(mTargetWidth, mTargetHeight);
        mMaxBetweenWidthAndHeight = Math.max(mTargetWidth, mTargetHeight);
        mRevealRadius = 0;
        mShouldDoAnimation = true;
        mIsPressed = true;
        mRevealRadiusGap = mMinBetweenWidthAndHeight / 8;

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0] - mLocationInScreen[0];
        int transformedCenterX = (int) mCenterX - left;
        mMaxRevealRadius = Math.max(transformedCenterX, mTargetWidth - transformedCenterX);
    }

    /**
     * 绘制水波
     */
    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        if (!mShouldDoAnimation || mTargetWidth <= 0 || mTouchTarget == null)
        {
            return;
        }

        if (mRevealRadius > mMinBetweenWidthAndHeight / 2)
        {
            mRevealRadius += mRevealRadiusGap * 4;
        } else
        {
            mRevealRadius += mRevealRadiusGap;
        }
        this.getLocationOnScreen(mLocationInScreen);
        int[] location = new int[2];
        mTouchTarget.getLocationOnScreen(location);
        // 获得要绘制View的left, top, right, bottom值
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + mTouchTarget.getMeasuredWidth();
        int bottom = top + mTouchTarget.getMeasuredHeight();

        canvas.save();
        canvas.clipRect(left, top, right, bottom);
        canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);
        canvas.restore();

        if (mRevealRadius <= mMaxRevealRadius)
        {
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        } else if (!mIsPressed)
        {
            mShouldDoAnimation = false;
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        // 获得相对于屏幕的坐标
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        // 获得动作
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN)
        {
            View touchTarget = getTouchTarget(this, x, y);
            if (touchTarget != null && touchTarget.isClickable() && touchTarget.isEnabled())
            {
                mTouchTarget = touchTarget;
                initParametersForChild(event, touchTarget);
                // 刷新界面，延迟执行时间
                postInvalidateDelayed(INVALIDATE_DURATION);
            }
        } else if (action == MotionEvent.ACTION_UP)
        {
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
            mDispatchUpTouchEventRunnable.event = event;
            postDelayed(mDispatchUpTouchEventRunnable, 40);
            return true;
        } else if (action == MotionEvent.ACTION_CANCEL)
        {
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 遍历view树找到用户所点击的那个view
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private View getTouchTarget(View view, int x, int y)
    {
        View target = null;
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews)
        {
            if (isTouchPointInView(child, x, y))
            {
                target = child;
                break;
            }
        }

        return target;
    }

    /**
     * 判断事件的xy是否落在view的上下左右四个角之内
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchPointInView(View view, int x, int y)
    {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && y >= top && y <= bottom && x >= left && x <= right)
        {
            return true;
        }
        return false;
    }

    // 使用代码主动去调用控件的点击事件（模拟人手去触摸控件）
    @Override
    public boolean performClick()
    {
        postDelayed(this, 400);
        return true;
    }

    @Override
    public void run()
    {
        super.performClick();
    }

    private class DispatchUpTouchEventRunnable implements Runnable
    {
        public MotionEvent event;

        @Override
        public void run()
        {
            if (mTouchTarget == null || !mTouchTarget.isEnabled())
            {
                return;
            }

            if (isTouchPointInView(mTouchTarget, (int) event.getRawX(), (int) event.getRawY()))
            {
                // 使用代码主动去调用控件的点击事件（模拟人手去触摸控件）
                mTouchTarget.performClick();
            }
        }
    };

}
