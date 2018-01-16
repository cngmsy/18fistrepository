package com.example.lenovo.android_a12;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button viewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewById = (Button) findViewById(R.id.btn);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quanxian();
            }
        });

    }

    private void quanxian() {
        boolean notificationEnabled = NotificationsUtils.isNotificationEnabled(this);
        if (notificationEnabled==true){
            Toast.makeText(this, "权限已开启", Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(this, "请打开通知栏权限", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(intent);
        }
    }
}
