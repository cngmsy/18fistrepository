package com.example.wsj.myhuanxindemotwo.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsj.myhuanxindemotwo.R;
import com.example.wsj.myhuanxindemotwo.adapter.ChatAdapter;
import com.example.wsj.myhuanxindemotwo.fragment.ChatAddFrafment;
import com.example.wsj.myhuanxindemotwo.fragment.ChatBQFragment;
import com.example.wsj.myhuanxindemotwo.fragment.ChatFragmentBig;
import com.example.wsj.myhuanxindemotwo.view.AudioRecorderButton;
import com.example.wsj.myhuanxindemotwo.view.MediaManager;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends FragmentActivity implements ChatFragmentBig.OnSendBigIcon {

    //标题显示栏的控件
    private TextView tv_title;
    //返回按钮
    private ImageView back;
    //显示聊天记录的listview控件
    private ListView chatLv;


    public ChatAdapter adapter;


    //发送按钮
    private Button send;
    //输入框的控件
    public static EditText inputtext;
    //当前聊天的好友的用户名
    private String name;
    //会话对象
    private EMConversation conversation;
    //声明并且实例化会话list集合
    private List<EMMessage> allMessages = new ArrayList<EMMessage>();
    private CheckBox chat_cb;
    private LinearLayout chat_ll;
    private CheckBox chat_cb_add;
    private FrameLayout chat_fl;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ChatBQFragment bq;
    private ChatAddFrafment cd;
    public static Bitmap bm;

    private AudioRecorderButton mAudioRecorderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();


    }

    /**
     * 初始化View
     */
    private void initView() {


        ChatFragmentBig.SetOnSendBigIcon(this);

        supportFragmentManager = getSupportFragmentManager();

        name = getIntent().getStringExtra("name");

        conversation = EMClient.getInstance().chatManager().getConversation(name, EMConversation.EMConversationType.Chat, true);

        //设置接收聊天消息的监听器
        EMClient.getInstance().chatManager().addMessageListener(new ChatListener());


        mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.chat_view_btn);

        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                int a = (int) seconds;
                sendAudio(filePath, a, name);
                Toast.makeText(ChatActivity.this, filePath + "", Toast.LENGTH_SHORT).show();
            }
        });

        tv_title = (TextView) findViewById(R.id.common_title);
        back = (ImageView) findViewById(R.id.common_left_iv);
        chatLv = (ListView) findViewById(R.id.chat_listview);
        send = (Button) findViewById(R.id.btn_send);
        inputtext = (EditText) findViewById(R.id.edit_input);
        CheckBox btnyuyin = (CheckBox) findViewById(R.id.chat_btn_yuyin);
        final Button btnview = (Button) findViewById(R.id.chat_view_btn);

//        btnyuyin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btnview.setVisibility(View.VISIBLE);
//                inputtext.setVisibility(View.GONE);
//            }
//        });

        btnyuyin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btnview.setVisibility(View.VISIBLE);
                    inputtext.setVisibility(View.GONE);
                }else{
                    btnview.setVisibility(View.GONE);
                    inputtext.setVisibility(View.VISIBLE);
                }
            }
        });


        //设置标题
        tv_title.setText(name);
        //给返回按钮添加监听事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //给发送按钮设置监听事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = inputtext.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    //调用发送文本的方法
                    sendText(s, name);
                }

            }
        });



        //通过conversation对象获取会话记录
        allMessages = conversation.getAllMessages();

        //创建一个adapter对象
        adapter = new ChatAdapter(ChatActivity.this, allMessages);
        //给listview设置adapter
        chatLv.setAdapter(adapter);
        //刷新adapter
        chatLv.setSelection(adapter.getCount() - 1);






        chat_ll = (LinearLayout) findViewById(R.id.chat_ll);
        chat_fl = (FrameLayout) findViewById(R.id.chat_fl);
        chat_cb_add = (CheckBox) findViewById(R.id.chat_btn_add);
        chat_cb = (CheckBox) findViewById(R.id.chat_cb);
        chat_cb_add.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chat_cb.setChecked(false);
                chat_fl.setVisibility(View.VISIBLE);
                switchFrag(2);
            }
        });


        chat_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chat_fl.setVisibility(View.VISIBLE);
                switchFrag(1);
                chat_cb.setChecked(true);


            }
        });
        chat_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat_fl.setVisibility(View.GONE);
            }
        });


        inputtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                send.setVisibility(View.VISIBLE);
                chat_cb_add.setVisibility(View.GONE);
                if (TextUtils.isEmpty(inputtext.getText().toString())) {
                    send.setVisibility(View.GONE);
                    chat_cb_add.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    public void sendText(String s, final String name) {
        //创建一条文本消息,content为消息的文字内容,toChatUsername为对方用户或者群聊的id,
        EMMessage message = EMMessage.createTxtSendMessage(s, name);
        //设置消息的类型,单聊的类型是(Emmessage.ChatType.Chat)
        message.setChatType(EMMessage.ChatType.Chat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        conversation.markAllMessagesAsRead();
                        //通过conversation对象获取会话记录
                        allMessages = conversation.getAllMessages();

                        //创建一个adapter对象
                        adapter = new ChatAdapter(ChatActivity.this, allMessages);
                        //给listview设置adapter
                        chatLv.setAdapter(adapter);
                        //刷新adapter
                        adapter.notifyDataSetChanged();
                        chatLv.setSelection(adapter.getCount() - 1);
                        inputtext.setText("");

                    }
                });


            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    public void sendAudio(String filePath, int length, final String name) {
        //创建一条文本消息,content为消息的文字内容,toChatUsername为对方用户或者群聊的id,
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, name);
        //设置消息的类型,单聊的类型是(Emmessage.ChatType.Chat)
        message.setChatType(EMMessage.ChatType.Chat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        conversation.markAllMessagesAsRead();
                        //通过conversation对象获取会话记录
                        allMessages = conversation.getAllMessages();
                        adapter.setListener(allMessages);
                        //刷新adapter
                        chatLv.setSelection(adapter.getCount() - 1);

                    }
                });


            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    public void sendBigIcon(String info) {
        //创建一条文本消息,content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        final EMMessage message = EMMessage.createTxtSendMessage(info, name);
        //如果是群聊，设置chattype,默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        message.setAttribute("MESSAGE_ATTR_IS_BIG_EXPRESSION", true);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        allMessages = conversation.getAllMessages();

                        //创建一个adapter对象
                        //给listview设置adapter
                        adapter.setListener(allMessages);
                        chatLv.setAdapter(adapter);

                        //刷新adapter
                        adapter.notifyDataSetChanged();
                        chatLv.setSelection(adapter.getCount() - 1);
                    }
                });

            }

            @Override
            public void onError(int i, String s) {
                Log.i("发送错误报告", s);

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    private void sendImg(String imagePath, boolean flag, String toChatUsername) {
        //imagePath为图片本地路径，false为不发送原图(默认超过100k的图片会压缩后发给对方),需要发送原图传false
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
        //如果是群聊，设置chattype,默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allMessages = conversation.getAllMessages();
                        adapter.setListener(allMessages);
                        chatLv.setSelection(adapter.getCount() - 1);
                    }
                });

            }

            @Override
            public void onError(int i, String s) {
                Log.i("发送错误报告", s);

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
    private void senVideo(String videoPath, String thumbPath , int videoLength, String toChatUsername) {
        //imagePath为图片本地路径，false为不发送原图(默认超过100k的图片会压缩后发给对方),需要发送原图传false
        EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
        //如果是群聊，设置chattype,默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allMessages = conversation.getAllMessages();
                        adapter.setListener(allMessages);
                        chatLv.setSelection(adapter.getCount() - 1);
                    }
                });

            }

            @Override
            public void onError(int i, String s) {
                Log.i("发送错误报告", s);

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    private void sendmap(double latitude, double longitude, String locationAddress, String toChatUsername) {
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
        //如果是群聊，设置chattype,默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setAttribute("AA", true);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allMessages = conversation.getAllMessages();
                        adapter.setListener(allMessages);
                        chatLv.setSelection(adapter.getCount() - 1);
                    }
                });

            }

            @Override
            public void onError(int i, String s) {
                Log.i("发送错误报告", s);

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    private class ChatListener implements EMMessageListener {
        @Override
        public void onMessageReceived(List<EMMessage> list) {


//            Notification.Builder builder = new Notification.Builder(ChatActivity.this);
//            Intent intent = new Intent(ChatActivity.this,ChatActivity.class);
//            PendingIntent intent1 =PendingIntent.getActivity(ChatActivity.this,2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setAutoCancel(true);
//            builder.setContentTitle("标题");
//            builder.setContentText("推送消息的内容");
//            //设置提醒内容
//            builder.setTicker("您有一个新的消息");
//            //设置我们的pendingIntent
//            builder.setContentIntent(intent1);
//            //设置一个小图标(没有该属性无法正常发送，原因待考察)
//            builder.setSmallIcon(R.drawable.ee_1);
//            builder.setWhen(System.currentTimeMillis());
//            NotificationManager manager = (NotificationManager) getSystemService
//                    (ChatActivity.this.NOTIFICATION_SERVICE);
//            manager.notify(2,builder.build());


            String fromname = null;
            for (EMMessage message : list) {
                message.getChatType();
                //如果当前的消息类型是单聊的话
                if (EMMessage.ChatType.Chat == message.getChatType()) {
                    fromname = message.getFrom();
                }


                //如果当前的消息发送者是来自于我的聊天好友
                if (fromname.equals(name)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //将所有的聊天记录设置为已读状态
                            conversation.markAllMessagesAsRead();
                            allMessages = conversation.getAllMessages();
                            adapter = new ChatAdapter(ChatActivity.this, allMessages);
                            chatLv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            chatLv.setSelection(adapter.getCount() - 1);

                        }
                    });

                }
            }




        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    }

    private void switchFrag(int i) {
        fragmentTransaction = supportFragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);

        switch (i) {
            case 1:

                if (bq == null) {
                    bq = new ChatBQFragment();
                    fragmentTransaction.add(R.id.chat_fl, bq);
                } else {
                    fragmentTransaction.show(bq);
                }


                break;
            case 2:

                if (cd == null) {
                    cd = new ChatAddFrafment();
                    fragmentTransaction.add(R.id.chat_fl, cd);
                } else {
                    fragmentTransaction.show(cd);
                }


                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (bq != null) {
            fragmentTransaction.hide(bq);
        }
        if (cd != null) {
            fragmentTransaction.hide(cd);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {


            switch (requestCode) {
                case 1:
                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) { //是否有SD卡
                        String imgth = Environment.getExternalStorageDirectory() + "/image.jpg";
                        sendImg(imgth, false, name);
                    } else {
                        Toast.makeText(ChatActivity.this, "没有SD卡", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2:

                    try {
                        Uri originalUri = data.getData();        //获得图片的uri
                        Toast.makeText(ChatActivity.this, originalUri + "", Toast.LENGTH_SHORT).show();
                        bm = MediaStore.Images.Media.getBitmap(getContentResolver(), originalUri);        //显得到bitmap图片
                        //这里开始的第二部分，获取图片的路径：
                        String[] proj = {MediaStore.Images.Media.DATA};
                        //好像是Android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        String path = cursor.getString(column_index);
                        sendImg(path, false, name);
                    } catch (IOException e) {
                        Log.e("错误", e.toString());
                    }

                    break;

                case 3:
                    double latitude = data.getDoubleExtra("latitude", 0);
                    double longitude = data.getDoubleExtra("longitude", 0);
                    String c = data.getStringExtra("address");
                    Toast.makeText(ChatActivity.this, "精度:" + latitude + "纬度:" + longitude + "位置:" + c, Toast.LENGTH_SHORT).show();
                    sendmap(latitude, longitude, c, name);
                    break;

                case 4:
                    String path = data.getStringExtra("path");
                    if (TextUtils.isEmpty(path)) {
                        String videopath = data.getStringExtra("videoPath");
                        Toast.makeText(ChatActivity.this, videopath, Toast.LENGTH_SHORT).show();
                        senVideo(videopath,videopath,11,name);

                    }else{
                        String pic = data.getStringExtra("pic");
                        Log.e("----------", "onActivityResult: "+ pic );
                        Toast.makeText(ChatActivity.this, pic, Toast.LENGTH_SHORT).show();
                        senVideo(path,path,10,name);

                    }

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

}
