package com.hxtao.qmd.hxtpay.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.hxtao.qmd.hxtpay.R;

/**
 * @Description:
 * @Author: Cyf on 2017/1/20.
 */

public class RefreshLayout extends SwipeRefreshLayout {

    //滑动的最小距离
    private final int mScaledTouchSlop;
    //底部布局
    private final View footView;

    private ListView mListView;

    private OnLoadListener mOnLoadListener;


    //判断是否正在加载中
    private boolean isLoading;

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //填充底部加载布局
        footView = View.inflate(context, R.layout.view_footer, null);
        // 表示控件移动的最小距离，手移动的距离大于这个距离才能拖动控件
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //获取Lis,设置listview的位子
        if (mListView == null) {
            //判断容器里有多少个子View
            if (getChildCount() > 0) {
                // 判断第一个孩子是不是ListView
                if (getChildAt(0) instanceof ListView) {
                    // 创建ListView对象
                    mListView = (ListView) getChildAt(0);

                    // 设置ListView的滑动监听
                    setListViewOnScroll();
                }
            }
        }
    }

    /**
     * 在分发事件的时候处理子控件的触摸事件
     *
     * @param ev
     * @return
     */
    private float mDownY, mUpY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                // 移动的起点  当用户按下时作为滑动的起点
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE://移动
                //在滑动的时候判断是否能下拉加载更多
                //防止出现 刷新中 又加载 或者重复加载
                if (canLoadMore()) {
                    // 加载数据
                    loadData();
                }

                break;
            case MotionEvent.ACTION_UP://抬起
                // 移动的终点  当用户抬起时作为滑动的起点
                mUpY = getY();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param
     * @return
     * @description:判断是否满足加载更多的条件
     */
    private boolean canLoadMore() {
        // 1. 上拉状态
        boolean condition1 = (mDownY - mUpY) >= mScaledTouchSlop;
//        if (condition1){
//            System.out.println("是上拉状态");
//        }
        //2.当前页面可见itme是最后一个条目
        boolean condition2 = false;
        if (mListView != null && mListView.getAdapter() != null) {
            condition2 = mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }

//        if (condition2) {
//            System.out.println("是最后一个条目");
//        }

        boolean condition3 = !isLoading;
//        if (condition3) {
//            System.out.println("不是正在加载状态");
//        }

        return condition1 && condition2 && condition3;
    }

    /**
     * @description:处理加载数据的逻辑
     * @param
     * @return
     */
    private void loadData(){
//        System.out.println("加载数据...");
        if (mOnLoadListener != null) {
            // 设置加载状态，让布局显示出来
            setLoading(true);
            mOnLoadListener.onLoad();
        }
    }
    /**
     * @description:设置加载状态，是否加载传入boolean值进行判断
     * @param
     * @return
     */
    public void setLoading(boolean loading){
        //修改当前的状态
        isLoading=loading;
        if (isLoading){//正在加载  显示加载布局
            mListView.addFooterView(footView);
        }else {//隐藏加载布局
            mListView.removeFooterView(footView);

            //重置滑动的坐标
            mDownY=0;
            mUpY=0;
        }
    }

    /**
     * @description:设置listView的滑动监听
     * @param
     * @return
     */
    private void setListViewOnScroll(){
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override//listView滑动状态改变监听
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //滑动过程中判断是否能加载更多
                if(canLoadMore()){
                    //加载数据
                    loadData();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }


    /**
     * 上拉加载的接口回调
     */

    public interface OnLoadListener {
        void onLoad();
    }
    public void setOnLoadListener(OnLoadListener mLoadListener){
            this.mOnLoadListener=mLoadListener;
    }
}
