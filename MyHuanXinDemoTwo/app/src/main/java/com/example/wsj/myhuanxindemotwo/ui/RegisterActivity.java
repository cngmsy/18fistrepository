package com.example.wsj.myhuanxindemotwo.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wsj.myhuanxindemotwo.R;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by wsj on 2018/1/14.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Button login_btn;
    private String usernameString;
    private String passwordString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView();
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_btn);

        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                submit();
                final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage("正在注册...");
                pd.show();
                //调用sdk注册的方法,网络请求需要开启一个子线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().createAccount(usernameString,passwordString);
                            if (pd.isShowing()){
                                pd.dismiss();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                            //返回注册成功以后用户名
                            setResult(RESULT_OK,new Intent().putExtra("name",usernameString));
                            finish();

                        } catch (final HyphenateException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int errorCode = e.getErrorCode();
                                    if (errorCode == EMError.USER_ALREADY_EXIST){
                                        Toast.makeText(RegisterActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                                    }else if(errorCode == EMError.NETWORK_ERROR){
                                        Toast.makeText(RegisterActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
                                    }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
                                        Toast.makeText(RegisterActivity.this, "用户名非法", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    }

                                    if (pd.isShowing()){
                                        pd.dismiss();
                                    }

                                }
                            });
                        }
                    }
                }){}.start();
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
