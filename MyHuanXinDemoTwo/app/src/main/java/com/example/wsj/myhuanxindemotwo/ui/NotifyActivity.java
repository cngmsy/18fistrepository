package com.example.wsj.myhuanxindemotwo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wsj.myhuanxindemotwo.R;
import com.example.wsj.myhuanxindemotwo.adapter.NotifyAdapter;
import com.example.wsj.myhuanxindemotwo.bean.InviteMessage;
import com.example.wsj.myhuanxindemotwo.service.ContactService;

import java.util.List;

public class NotifyActivity extends Activity {

    //声明一个显示标题的文本控件
    private TextView title;
    //声明一个标题左边显示返回按钮的控件
    private ImageView back;
    //声明一个用来显示好友邀请消息的列表
    private ListView contact_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        initView();
    }

    /**
     * 初始化布局中控件
     */
    private void initView() {
        title = (TextView) findViewById(R.id.common_title);
        back = (ImageView) findViewById(R.id.common_left_iv);
        contact_listview = (ListView) findViewById(R.id.notify_listview);
        title.setText("申请与通知");
        //给标题栏左边的控件设置图片
        back.setImageResource(R.mipmap.em_back);
        //给返回按钮添加监听事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //获取数据源(好友邀请的所有消息)
        List<InviteMessage> list = ContactService.list;
        //给listview设置adapter
        contact_listview.setAdapter(new NotifyAdapter(NotifyActivity.this,list));

    }
}
