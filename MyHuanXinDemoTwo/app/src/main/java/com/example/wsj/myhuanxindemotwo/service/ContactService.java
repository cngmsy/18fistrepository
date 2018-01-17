package com.example.wsj.myhuanxindemotwo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.wsj.myhuanxindemotwo.bean.InviteMessage;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/6.
 */
public class ContactService extends Service {
    private static final String TAG = "ContactService";
    //声明并创建一个List集合
    public static List<InviteMessage> list = new ArrayList<InviteMessage>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());

        return super.onStartCommand(intent, flags, startId);

    }

    private class MyContactListener implements EMContactListener {
        //添加联系人
        @Override
        public void onContactAdded(String s) {

        }

        //删除联系人
        @Override
        public void onContactDeleted(String s) {

        }

        //联系人邀请的消息
        @Override
        public void onContactInvited(String s, String s1) {
            Log.i(TAG, "onContactInvited: 监听方法被调用");
            Log.i(TAG, "onContactInvited: "+s+s1 );
            //循环遍历list集合(如果已经存在,这个邀请邀请消息,从集合里面删除)
            for (int i = 0; i < list.size(); i++) {
                if (s.equals(list.get(i).getFromname())){
                    list.remove(i);
                }

            }

            //实例化一个InviteMessage的对象
            InviteMessage message = new InviteMessage();

            //给这条邀请的消息 设置消息来源(发送者)
            message.setFromname(s);
            //设置附加的邀请理由
            message.setReason(s1);
            //设置系统当前的时间
            message.setTime(System.currentTimeMillis());
            //设置默认未处理之前同意按钮的状态(未点击)
            message.setAgree(false);
            //设置默认未处理之前拒绝按钮的状态(未点击)
            message.setRefuse(false);
            list.add(message);



        }

        //同意好友
        @Override
        public void onContactAgreed(String s) {
            InviteMessage msg = new InviteMessage();
            msg.setFromname(s);
            msg.setReason(s+"已同意了您的好友请求");
            msg.setTime(System.currentTimeMillis());
            msg.setAgree(true);
            msg.setRefuse(false);
            list.add(msg);
        }

        //拒绝请求
        @Override
        public void onContactRefused(String s) {
            InviteMessage msg = new InviteMessage();
            msg.setFromname(s);
            msg.setReason(s+"已拒绝了您的好友请求");
            msg.setTime(System.currentTimeMillis());
            msg.setAgree(false);
            msg.setRefuse(true);
            list.add(msg);
        }
    }
}
