package com.example.dawei.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/1/11.
 */
//封装的BaseView
public abstract class BaseView extends View {

    private MyThread myThread;

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //提取绘制操作
    protected abstract void drawSub(Canvas canvas);

    //提取逻辑处理
    protected abstract void Logic();

    @Override
    protected void onDraw(Canvas canvas) {

        if (myThread == null) {
            myThread = new MyThread();
            //启动线程
            myThread.start();
        } else {
            drawSub(canvas);
        }
    }


    //当屏幕离开时，While循环关闭
    @Override
    protected void onDetachedFromWindow() {
        running = false;
        super.onDetachedFromWindow();
    }

    private boolean running = true;

    class MyThread extends Thread {
        @Override
        public void run() {
            while (running) {
                //逻辑处理
                Logic();
                //重新绘制
                postInvalidate();
                try {
                    myThread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
