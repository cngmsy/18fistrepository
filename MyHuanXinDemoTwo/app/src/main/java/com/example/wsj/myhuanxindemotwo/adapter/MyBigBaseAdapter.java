package com.example.wsj.myhuanxindemotwo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.wsj.myhuanxindemotwo.R;

import java.util.List;

/**
 * Created by user on 2016/7/8.
 */
public class MyBigBaseAdapter extends BaseAdapter {
    Context context;
    List<Integer> list;

    public MyBigBaseAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view =  View.inflate(context, R.layout.chat_frag_big_item,null);
        ImageView iv = (ImageView) view.findViewById(R.id.chat_frag_big_iv);
        iv.setImageResource(list.get(i));
        return view;
    }
}
