package demo.example.com.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/*
* Autolayout是一种布局技术，专门用来布局UI界面的。用来取代Frame布局在遇见屏幕尺寸多重多样的不足
* */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 使用智能适配方法，自动处理不同屏幕尺寸和密度
        AutoUtils.smartSetSize(this, false, 768, 1280);//智能适配各种屏幕
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                startActivity(new Intent(MainActivity.this,DemoActivity.class));
            }

        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                startActivity(new Intent(MainActivity.this,ListViewActivity.class));
            }

        });

    }
}
