package com.example.lenovo.my_svg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;

public class MainActivity extends AppCompatActivity {

    private PathView pv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        pv.getPathAnimator()
                .delay(400)//几秒画一次
                .duration(4000)//画多久
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    //listenerEnd用这个方法控制此Activity跳转
                    public void onAnimationEnd() {
                        startActivity(new Intent(MainActivity.this, Main2Activity.class));
                    }
                })
                .interpolator(new AccelerateDecelerateInterpolator())

                .start();


    }

    private void initView() {
        pv = (PathView) findViewById(R.id.pv);
    }
}
