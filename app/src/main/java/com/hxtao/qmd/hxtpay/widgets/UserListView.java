package com.hxtao.qmd.hxtpay.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @Description:
 * @Author: Cyf on 2017/3/6.
 */

public class UserListView extends ListView {

    private int mWidth = 0;

    public UserListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        for (int i = 0; i < getChildCount(); i++) {
            int measuredWidth = getChildAt(i).getMeasuredWidth();
            mWidth = Math.max(mWidth, measuredWidth);
        }

        setMeasuredDimension(mWidth, measuredHeight);
    }

    /**
     * 设置宽度，如果不设置，则默认包裹内容
     *
     * @param width 宽度
     */
    protected void setListWidth(int width) {
        mWidth = width;
    }
}
