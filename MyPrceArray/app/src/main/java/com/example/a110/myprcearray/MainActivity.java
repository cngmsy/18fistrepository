package com.example.a110.myprcearray;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private PrceArray test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        test = (PrceArray) this.findViewById(R.id.test);
    }
    public void start(View v) {
        test.startScroll();
    }

    public void stop(View v) {
        test.stopScroll();
    }
    public void startFor0(View v){
        test.startFor0();
    }
}
