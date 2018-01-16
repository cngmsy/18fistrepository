package com.example.lenovo.k_xiantu;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private KLineView k_line_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        k_line_view = (KLineView) findViewById(R.id.k_line_view);
        List<Point> mPoints = new ArrayList<>();
        mPoints.add(new Point(20,30));
        mPoints.add(new Point(100,200));
        mPoints.add(new Point(130,280));
        mPoints.add(new Point(200,90));
        mPoints.add(new Point(240,300));
        mPoints.add(new Point(300,360));
        mPoints.add(new Point(350,210));
        mPoints.add(new Point(390,60));
        k_line_view.setDatas(mPoints);

    }

}

