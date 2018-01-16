package com.example.administrator.lunpandemoone;

import android.app.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LuckyPan mLuckyPanView;
    private ImageView mStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLuckyPanView = (LuckyPan) findViewById(R.id.id_luckypan);
        mStartBtn = (ImageView) findViewById(R.id.id_start_btn);

        mStartBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!mLuckyPanView.isStart())
                {
                    mStartBtn.setImageResource(R.drawable.stop);
                    mLuckyPanView.luckyStart(1);
                }else {
                    if (!mLuckyPanView.isShouldEnd()){
                        mLuckyPanView.luckyEnd();
                        mStartBtn.setImageResource(R.drawable.start);


                    }
                }

            }
        });

    }
}
