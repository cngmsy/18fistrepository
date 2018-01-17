package com.example.wsj.myhuanxindemotwo.adapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsj.myhuanxindemotwo.R;
import com.example.wsj.myhuanxindemotwo.bean.InviteMessage;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * Created by user on 2016/7/6.
 */
public class NotifyAdapter extends BaseAdapter {

    private Context context;
    private List<InviteMessage> list;
    public NotifyAdapter(Context context, List<InviteMessage> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list!= null ? list.size():0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view == null){
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_notify, null);
            //关联item布局中的同意按钮
            vh.agree = (Button) view.findViewById(R.id.btn_agree);
            //关联item布局中的拒绝按钮
            vh.refuse = (Button)view.findViewById(R.id.btn_refuse);
            //关联item布局中显示发送者的名字文本控件
            vh.fromname = (TextView)view.findViewById(R.id.item_tv_name);
            //关联item布局中的显示添加理由的文本控件
            vh.reason = (TextView) view.findViewById(R.id.item_tv_xx);
            //关联item布局中的显示好友状态的文本控件
            vh.result = (TextView) view.findViewById(R.id.tv_result);
            view.setTag(vh);


        }else{

            vh = (ViewHolder) view.getTag();
        }
        final InviteMessage message = list.get(i);
        if(message.isAgree()){
            //我点击了同意按钮,再次进入申请与通知的界面还会显示同意信息
            vh.agree.setVisibility(View.GONE);
            vh.refuse.setVisibility(View.GONE);
            vh.result.setVisibility(View.VISIBLE);
            vh.result.setText("已同意");
        }else if(message.isRefuse()){
            //点击拒绝按钮,在次进入通知与申请界面还会显示拒绝信息
            vh.agree.setVisibility(View.GONE);
            vh.refuse.setVisibility(View.GONE);
            vh.result.setVisibility(View.VISIBLE);
            vh.result.setText("已拒绝");
        }else{
            //未处理之前让两个按钮都显示
            vh.agree.setVisibility(View.VISIBLE);
            vh.refuse.setVisibility(View.VISIBLE);
        }




        //设置发送者的名字
        vh.fromname.setText(message.getFromname());
        //设置添加好友的理由
        vh.reason.setText(message.getReason());

        final ViewHolder vh1 = vh;
        vh.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //给同意按钮添加监听事件
//                vh1.agree.setVisibility(View.GONE);
//                vh1.refuse.setVisibility(View.GONE);
//                vh1.result.setVisibility(View.VISIBLE);
//                vh1.result.setText("已同意");


                acceptInvite(vh1.agree,vh1.refuse,vh1.result,message);
//                notifyDataSetChanged();

            }
        });
        vh.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //给拒绝按钮添加监听事件
                refuseInvite(vh1.agree,vh1.refuse,vh1.result,message);
            }
        });


        return view;
    }

    private void refuseInvite(Button agree, Button refuse, TextView result, InviteMessage message) {
        try {
            //调用sdk提供的拒绝好友的方法
            EMClient.getInstance().contactManager().declineInvitation(message.getFromname());
            agree.setVisibility(View.GONE);
            refuse.setVisibility(View.GONE);
            result.setVisibility(View.VISIBLE);
            result.setText("已拒绝");
            message.setAgree(false);
            message.setRefuse(true);
            notifyDataSetChanged();


        } catch (HyphenateException e) {
            e.printStackTrace();
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    //点击同意按钮以后的逻辑操作
    private void acceptInvite(Button agree, Button refuse, TextView result, InviteMessage message) {
        try {

            EMClient.getInstance().contactManager().acceptInvitation(message.getFromname());

            agree.setVisibility(View.GONE);
            refuse.setVisibility(View.GONE);
            result.setVisibility(View.VISIBLE);

            result.setText("已同意");
            message.setAgree(true);
            message.setRefuse(false);
            notifyDataSetChanged();

        } catch (HyphenateException e) {
            e.printStackTrace();
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "同意失败", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    static class ViewHolder{
        //发送者
        TextView fromname;
        //添加理由
        TextView result;
        //添加结果
        TextView reason;
        //同意按钮
        Button agree;
        //拒绝按钮
        Button refuse;
    }
}
