package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 110 on 2018/1/15.
 */

public class VMarqueeView extends ListView {
    private int mDelayExChange = 1000; //滚动时间
    private int mDelayKeep = 3000;//停顿时间
    //    private Handler mHandler = new Handler();//View中不建议使用Handler
    private VMarqueeRunnable mMarRunnable = new VMarqueeRunnable();


    public VMarqueeView(Context context) {
        super(context);
        init();
    }

    public VMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VMarqueeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        return true;
                }
                return false;
            }
        });

        post(new Runnable() {
            @Override
            public void run() {
                runScroll();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //该方法在onDraw之前任何时候调用，不确定在onMeasure之前还是之后调用。
//        Log.i("zjm", "onAttachedToWindow run");
//        runScroll();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //当包含此View的Activity退出或View被remove后调用
        removeCallbacks(mMarRunnable);
    }

    public void setelayExChange(int delayExChange) {
        this.mDelayExChange = delayExChange;
    }

    public void setDelayKeep(int delayKeep) {
        this.mDelayKeep = delayKeep;
    }

    public void runScroll() {
        postDelayed(mMarRunnable, mDelayKeep);
    }

    private class VMarqueeRunnable implements Runnable {

        @Override
        public void run() {
            boolean canScrollVertically = VMarqueeView.this.canScrollList(1);
            if (canScrollVertically && getChildCount() > 0) {
                Log.i("zjm", "canScrollVertically：" + canScrollVertically + "  执行滚动");
                int itemHeight = getChildAt(0).getMeasuredHeight();
                VMarqueeView.this.smoothScrollBy(itemHeight + getDividerHeight(), mDelayExChange);
            } else {
                Log.i("zjm", "canScrollVertically：" + canScrollVertically + "  停止滚动");
            }
            runScroll();
        }
    }

    /**
     * 无限循环Adapter
     * @param <T>
     */
    public static abstract class LooperAdapter<T> extends BaseAdapter {
        private List<T> mDatas = new ArrayList<>();

        public LooperAdapter(List<T> mDatas) {
            this.mDatas = mDatas;
        }

        public LooperAdapter() {
        }

        public void setDatas(List<T> datas) {
            this.mDatas = datas;
        }

        @Override
        public int getCount() {
            return mDatas.size() > 0 ? Integer.MAX_VALUE : 0;
        }

        @Override
        public T getItem(int position) {
            return mDatas.size() > 0 ? mDatas.get(position % mDatas.size()) : null;
        }

        @Override
        public long getItemId(int position) {
            return mDatas.size() > 0 ? position % mDatas.size() : position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getView(getItem(position), convertView, parent);
        }

        public abstract View getView(T item, View convertView, ViewGroup parent);
    }
}
