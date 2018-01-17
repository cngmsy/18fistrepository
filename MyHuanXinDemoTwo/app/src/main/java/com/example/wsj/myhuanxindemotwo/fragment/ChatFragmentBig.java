package com.example.wsj.myhuanxindemotwo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.wsj.myhuanxindemotwo.R;
import com.example.wsj.myhuanxindemotwo.adapter.MyBigBaseAdapter;
import com.example.wsj.myhuanxindemotwo.utils.EmoUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 2016/7/8.
 */
public class ChatFragmentBig extends Fragment {

    private static OnSendBigIcon sendBigIcon;
    private GridView gv;
    MyBigBaseAdapter adapter;
    public static List<Integer> gvList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        gvList.clear();
        for (int i = 0; i < EmoUtils.bigIcons.length; i++) {
            gvList.add(EmoUtils.bigIcons[i]);
        }

        View view = View.inflate(getActivity(), R.layout.chat_frag_big, null);
        gv = (GridView) view.findViewById(R.id.chat_frag_big_gv);

        adapter = new MyBigBaseAdapter(getActivity(), gvList);
        gv.setAdapter(adapter);



        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sendBigIcon.sendBigIcon(gvList.get(i)+"");

            }
        });

        return view;
    }



    public static void SetOnSendBigIcon(OnSendBigIcon onSendBigIcon) {
        sendBigIcon = onSendBigIcon;
    }



    public interface OnSendBigIcon {
        public void sendBigIcon(String info);
    }



}