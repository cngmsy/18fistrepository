package com.example.lenovo.android_zhibiao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //设置一个初始化值
    private static final float PERCENT = 60;
    private ImageView ivIndex;
    private TextView tvRatio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取组件Id
        ivIndex = (ImageView) findViewById(R.id.iv_index);
        tvRatio = (TextView) findViewById(R.id.tv_ratio);
        //给TextView赋值
        tvRatio.setText(PERCENT + "");
        //调用ivRotate（）方法
        ivRotate(PERCENT);
    }
    /**
     * 旋转动画
     * @param percent
     */
    //定义一个方法，参数为double类型
    private void ivRotate(double percent) {
        //三元运算
        //判断percent是否大于100，为true时走左，为false时走右边；
        double percentOffset = percent > 100 ? 100 : percent;
        //用代码编写旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180 * ((int) percentOffset / 100f),
                Animation.RELATIVE_TO_SELF, 0.748f, Animation.RELATIVE_TO_SELF, 0.5f);
        //设置执行时间
        rotateAnimation.setDuration(3000);
        //动画结束后，停留在最后一帧
        rotateAnimation.setFillAfter(true);
        //开始动画
        ivIndex.startAnimation(rotateAnimation);
    }
}
