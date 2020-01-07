package com.superc.waitmarket.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class InConstranLayout extends ConstraintLayout {

    public boolean mIsIntercept = false;//是否拦截子项点击事件 默认不拦截
    public InConstranLayout(Context context) {
        super(context);
    }

    public InConstranLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InConstranLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return mIsIntercept;//true就是拦截  false  就是不拦截，拦截的意思是事件不会继续往下分发，如果当前View，处理这个点击事件,则事件到此终止，如果不处理这次事件，则事件会继续往上传递，不会往下分发了
    }

    public boolean ismIsIntercept() {
        return mIsIntercept;
    }

    public void setmIsIntercept(boolean mIsIntercept) {
        this.mIsIntercept = mIsIntercept;//这个是提供的一个外部的一个入口，来判断父布局是否拦截当前的事件
    }
}
