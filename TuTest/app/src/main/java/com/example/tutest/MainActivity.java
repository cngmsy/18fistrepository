package com.example.tutest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.dstImageView)
    ImageView dstimage;
    @BindView(R.id.Saturationseekbar)
    SeekBar Saturationseekbar;
    @BindView(R.id.Brightnessseekbar)
    SeekBar Brightnessseekbar;
    @BindView(R.id.Contrastseekbar)
    SeekBar Contrastseekbar;

    private Bitmap tuBitmap;

    private int imgHeight, imgWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tuBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        dstimage.setImageBitmap(tuBitmap);
        imgHeight = tuBitmap.getHeight();
        imgWidth = tuBitmap.getWidth();
        Saturationseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // 当拖动条的滑块位置发生改变时触发该方法
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
                Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
                ColorMatrix cMatrix = new ColorMatrix();
                // 设置饱和度
                cMatrix.setSaturation((float)(progress/100.0));
                Paint paint = new Paint();
                paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
                Canvas canvas = new Canvas(bmp);
                // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
                canvas.drawBitmap(tuBitmap,0,0,paint);

                dstimage.setImageBitmap(bmp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Brightnessseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                // 当拖动条的滑块位置发生改变时触发该方法  
                Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
                int brightness = progress - 127;
                ColorMatrix cMatrix = new ColorMatrix();
                cMatrix.set(new float[]{ 1, 0, 0, 0, brightness, 0, 1,
                        0, 0, brightness,// 改变亮度
                        0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
                Paint paint = new Paint();
                paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
                Canvas canvas = new Canvas(bmp);
                canvas.drawBitmap(tuBitmap,0,0,paint);
                dstimage.setImageBitmap(bmp);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Contrastseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
                float contrast = (float) ((progress + 64) / 128.0);
                ColorMatrix cMatrix = new ColorMatrix();
                cMatrix.set(new float[]{ contrast, 0, 0, 0, 0, 0,
                        contrast, 0, 0, 0,// 改变对比度
                        0, 0, contrast, 0, 0, 0, 0, 0, 1, 0 });
                Paint paint = new Paint();
                paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
                Canvas canvas = new Canvas(bmp);
                canvas.drawBitmap(tuBitmap,0,0,paint);
                dstimage.setImageBitmap(bmp);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
