package com.example.dawei.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/1/15.
 */

public class MyTest extends BaseView {
    private Paint paint = new Paint();
    private float rx = 0;

    public MyTest(Context context) {
        super(context);
    }

    public MyTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void drawSub(Canvas canvas) {
        paint.setTextSize(30);
        canvas.drawText("你们没有女朋友啊！", rx, 30, paint);
    }

    @Override
    protected void Logic() {
        rx += 3;
        if (rx > getWidth()) {
            rx = -paint.measureText("你们没有女朋友啊！");
        }
    }
}
