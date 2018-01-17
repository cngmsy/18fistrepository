package com.example.wsj.myhuanxindemotwo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.wsj.myhuanxindemotwo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2016/7/8.
 */
public class MyBaseAdapter extends BaseAdapter {
    Context context;
    List<Integer> list;
    public static List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
    Map<String,Object> listItem = new HashMap<String,Object>();

    public MyBaseAdapter(Context context, List<Integer> list) {
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
        view =  View.inflate(context, R.layout.chat_fragment_item,null);
        ImageView iv = (ImageView) view.findViewById(R.id.item_chat_frag_iv1);
        iv.setImageResource(list.get(i));
        listItems.add(listItem);
        return view;
    }
}
