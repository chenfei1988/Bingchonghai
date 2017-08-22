package com.tobacco.xinyiyun.knowledge.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by YangQiang on 2017/8/8.
 */

public class NoScollViewPager extends ViewPager {
    private boolean isSlide = false;


    public NoScollViewPager(Context context) {
        super(context);
    }

    public NoScollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isSlide && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isSlide && super.onInterceptTouchEvent(event);
    }

    public void setSlide(boolean b) {
        this.isSlide = b;
    }
}
