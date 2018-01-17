package com.example.dawei.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/1/11.
 */

public class DemoView extends View {
    private Bitmap bitmap;
    private Paint paint = new Paint();

    public DemoView(Context context) {
        super(context);

    }

    public DemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }




    @Override
    protected void onDraw(Canvas canvas) {
        //Paint   通过paint对象 影响绘制元素的样式信息！
        paint.setTextSize(50);
        //绘制文字
        canvas.drawText("共产党好啊！", 0, 50, paint);
        //绘制直线(1-2 代表的起始坐标,3-4代表的是终止坐标)
        canvas.drawLine(0, 60, 100, 60, paint);
        //设置空心
//        paint.setStyle(Paint.Style.STROKE);
        //绘制矩形（）
        canvas.drawRect(0, 90, 100, 190, paint);
        //绘制圆角矩形
        RectF rectF = new RectF(0, 90, 100, 190);
        canvas.drawRoundRect(rectF, 10, 10, paint);
        //绘制圆形(1-2 圆心，3 半径是50的话)
        canvas.drawCircle(50, 270, 50, paint);


        //绘制图片
//        canvas.drawBitmap(bitmap, 0,350,paint);


    }

}
