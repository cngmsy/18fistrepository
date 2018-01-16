package com.jiyun.jiashiling.myapplication01.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiyun.jiashiling.myapplication01.MainActivity;
import com.jiyun.jiashiling.myapplication01.R;
import com.jiyun.jiashiling.myapplication01.beans.Person;

import java.util.List;

/**
 * Created by admin on 2018/1/16.
 */

public class SubListAdapter extends BaseAdapter {
    private List<Person> lists;
    private Context context;
    private ViewHolder viewHolder;

    public SubListAdapter(MainActivity mainActivity, List<Person> lists) {
        this.lists = lists;
        this.context = mainActivity;
    }

    @Override
    public int getCount() {
        return lists.size();
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
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String word = lists.get(i).getHeaderWord();
        viewHolder.tv_word.setText(word);
        viewHolder.tv_name.setText(lists.get(i).getName());
        //将相同字母开头的合并在一起
        if (i == 0) {
            //第一个是一定显示的
            viewHolder.tv_word.setVisibility(View.VISIBLE);
        } else {
            //后一个与前一个对比,判断首字母是否相同，相同则隐藏
            String headerWord = lists.get(i - 1).getHeaderWord();
            if (word.equals(headerWord)) {
                viewHolder.tv_word.setVisibility(View.GONE);
            } else {
                viewHolder.tv_word.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_word;
        public TextView tv_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_word = (TextView) rootView.findViewById(R.id.tv_word);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
