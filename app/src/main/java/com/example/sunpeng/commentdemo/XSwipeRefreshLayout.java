package com.example.sunpeng.commentdemo;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sunpeng on 16-11-8.
 */

public class XSwipeRefreshLayout extends SwipeRefreshLayout {
    private OnTouchCallBack mOnTouchCallBack;

    public XSwipeRefreshLayout(Context context) {
        super(context);
    }

    public XSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mOnTouchCallBack != null && ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (mOnTouchCallBack.onTouch(ev))
                return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public OnTouchCallBack getmOnTouchCallBack() {
        return mOnTouchCallBack;
    }

    public void setmOnTouchCallBack(OnTouchCallBack mOnTouchCallBack) {
        this.mOnTouchCallBack = mOnTouchCallBack;
    }

    public interface OnTouchCallBack {
        boolean onTouch(MotionEvent event);
    }
}
