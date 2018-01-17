package com.example.wsj.myhuanxindemotwo.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wsj.myhuanxindemotwo.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private Button login_btn;
    private Button register_btn;
    private String usernameString;
    private String passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn = (Button) findViewById(R.id.register_btn);

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                submit();
                //创建一个进度条对话框实例
                final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                //设置点击进度条边框以外的区域无响应
                pd.setCanceledOnTouchOutside(false);
                //添加对话框提示的消息
                pd.setMessage("正在登录...");
                //显示对话框
                pd.show();

                EMClient.getInstance().login(usernameString, passwordString, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //成功的时候判断对话框是否显示,如果显示关闭对话框
                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                        //刷新UI界面(除了可以用Handler,还可以用runOnUIThread()这个方法)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                            }
                        });
                        //创建一个Intent对象,实现从登录界面跳转到我们的主界面
                        Intent intent = new Intent(MainActivity.this, ContactFragment.class);
                        startActivity(intent);
                        //跳转界面以后关闭当前的界面
                        finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });

                break;
            case R.id.register_btn:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
        }
    }

    private void submit() {
        // validate
        usernameString = username.getText().toString().trim();
        if (TextUtils.isEmpty(usernameString)) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        }

        passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
