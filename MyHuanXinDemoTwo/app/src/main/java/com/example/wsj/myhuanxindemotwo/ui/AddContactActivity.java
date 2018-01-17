package com.example.wsj.myhuanxindemotwo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsj.myhuanxindemotwo.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;


/**
 * Created by user on 2016/7/5.
 */
public class AddContactActivity extends Activity {

    //标题栏的返回按钮
    private ImageView back;
    //标题栏右边的 文本控件(查找)
    private TextView search;
    //布局中的输入框控件
    private EditText edit_input;
    //布局中默认隐藏的View
    private LinearLayout linear_layout;
    //默认隐藏布局中的文本控件
    private TextView tv_name;
    //添加按钮
    private Button btn_add;
    private String stringName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);

        initView();
    }

    //初始化布局中的相关控件
    private void initView() {
        back = (ImageView) findViewById(R.id.iv_back);
        search = (TextView) findViewById(R.id.search_tv);
        edit_input = (EditText) findViewById(R.id.edit_input);
        linear_layout = (LinearLayout) findViewById(R.id.linear_layout);
        tv_name = (TextView) findViewById(R.id.tv_name);
        btn_add = (Button) findViewById(R.id.btn_add);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //查找按钮的监听事件
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stringName = edit_input.getText().toString().trim();
                if (TextUtils.isEmpty(stringName)){
                    Toast.makeText(AddContactActivity.this, "请输入要查找的用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                linear_layout.setVisibility(View.VISIBLE);
                tv_name.setText(stringName);
            }
        });

        //添加按钮的监听事件
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String reason = "我是"+ EMClient.getInstance().getCurrentUser()+",加个好友呗";

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //调用sdk提供的添加好友的方法
                            EMClient.getInstance().contactManager().addContact(stringName,reason);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddContactActivity.this, "添加好友请求发送成功,等待对方回应", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddContactActivity.this, "添加好友失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }).start();



            }
        });

    }
}
