package com.example.wsj.myhuanxindemotwo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wsj.myhuanxindemotwo.R;

import java.util.List;

/**
 * Created by user on 2016/7/7.
 */
public class ContactAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public ContactAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_contact, null);
            vh.name = (TextView) view.findViewById(R.id.item_contact_tv_name);
            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }

        String s = list.get(i);
        vh.name.setText(s);

        return view;
    }

    static class ViewHolder{
        TextView name;
    }


}
