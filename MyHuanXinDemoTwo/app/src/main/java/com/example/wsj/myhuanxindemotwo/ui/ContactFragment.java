package com.example.wsj.myhuanxindemotwo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wsj.myhuanxindemotwo.R;
import com.example.wsj.myhuanxindemotwo.adapter.ContactAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/5.
 */
public class ContactFragment extends AppCompatActivity {
    //通用标题栏右边的图标控件
    private ImageView common_right;
    private TextView common_title;
    //布局中用来显示好友列表的listView
    private ListView contact_listview;
    //listview的一个头布局
    private View headerview;
    //申请与通知的条目
    private LinearLayout apply_item;
    //群聊条目
    private LinearLayout group_item;
    //聊天室条目
    private LinearLayout char_room_item;
    //环信小助手条目
    private LinearLayout robot_item;
    //好友列表的集合
    private List<String> allContactsFromServer;
    private ContactAdapter adapter;

    Handler handler = new Handler(new Handler.Callback() {


        @Override
        public boolean handleMessage(Message message) {
            //创建一个adapter对象
            adapter = new ContactAdapter(ContactFragment.this, allContactsFromServer);
            //给listview设置adapter
            contact_listview.setAdapter(adapter);
            //当数据源发生改变时,刷新adapter
            adapter.notifyDataSetChanged();
            return false;
        }
    });
    private boolean hidden1 = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact);
        getData();
        initView();
    }


    //获取好友列表
    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //调用sdk的获取好友列表的方法.返回list集合
                    allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    //发送一个消息
                    handler.sendEmptyMessage(0);

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }





    @Override
    public void onResume() {
        super.onResume();
        if (hidden1 == false) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
                        handler.sendEmptyMessage(0);
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 初始化布局中的View(包括各种需要关联的控件)
     */
    private void initView() {
        //从布局里面关联到右边的图标控件
        common_right = (ImageView) findViewById(R.id.common_right_iv);
        //给关联到的图标控件设置图片
        common_right.setImageResource(R.mipmap.em_add);
        //从布局里面关联到中间的文本控件
        common_title = (TextView) findViewById(R.id.common_title);
        //给关联到的文本控件设置标题
        common_title.setText("通讯录");

        contact_listview = (ListView) findViewById(R.id.contact_listview);

        //获取listview的头布局
        headerview = LayoutInflater.from(this).inflate(R.layout.default_item, null);
        //获取头布局中的申请与通知的这个条目
        apply_item = (LinearLayout) headerview.findViewById(R.id.apply_item);


        //创建一个监听器的实现类的对象
        HeaderListener headerListener = new HeaderListener();

        //分别给头布局的四个条目设置监听事件
        apply_item.setOnClickListener(headerListener);
        common_right.setOnClickListener(headerListener);
        contact_listview.addHeaderView(headerview);
        adapter = new ContactAdapter(ContactFragment.this, allContactsFromServer);
        allContactsFromServer = new ArrayList<>();
        contact_listview.setAdapter(null);

        /**
         * 好友列表Item的点击事件
         */
        contact_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ContactFragment.this, ChatActivity.class);
                intent.putExtra("name", allContactsFromServer.get(i - 1));
                startActivity(intent);
            }
        });
    }


    /**
     *
     */
    private class HeaderListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //点击头布局中的申请与通知的条目,进行跳转
                case R.id.apply_item:
                    Intent intent = new Intent(ContactFragment.this, NotifyActivity.class);
                    startActivity(intent);
                    break;
                //点击头布局中的群聊的条目,进行跳转到群聊界面


                //点击标题栏的右边的+号,跳转到添加好友的界面
                case R.id.common_right_iv:
                    Intent intent1 = new Intent(ContactFragment.this, AddContactActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    }
}
