package com.example.dawei.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Random;

/**
 * Created by Administrator on 2018/1/15.
 */

public class LogicView extends BaseView {
    //初始化画笔,准备画你想画的东西了。
    private Paint paint = new Paint();
    private float rx = 0;
    // 画圆里面的参数
    private RectF rectF = new RectF(0, 60, 100, 160);
    private float sweepAngle = 0;
    Random random = new Random();

    public LogicView(Context context) {
        super(context);
    }

    public LogicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void drawSub(Canvas canvas) {
        //设置字体大小
        paint.setTextSize(50);
        //字体
        canvas.drawText("共产党顶呱呱啊！", rx, 60, paint);
        //圆
        canvas.drawArc(rectF, 0, sweepAngle, true, paint);


    }

    @Override
    protected void Logic() {
        //逻辑
        rx += 3;
        // 如果rx坐标超出了,我就给它重新赋值,为字体的-的
        if (rx > getWidth()) {
            rx = -paint.measureText("共产党顶呱呱啊！");
        }
        sweepAngle++;
        if (sweepAngle > 360) {
            sweepAngle = 0;
        }

        //随机颜色
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        paint.setARGB(255, r, g, b);
    }
}
