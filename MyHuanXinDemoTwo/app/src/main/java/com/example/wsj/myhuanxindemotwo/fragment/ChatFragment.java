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
import com.example.wsj.myhuanxindemotwo.adapter.MyBaseAdapter;
import com.example.wsj.myhuanxindemotwo.ui.ChatActivity;
import com.example.wsj.myhuanxindemotwo.utils.EmoUtils;
import com.example.wsj.myhuanxindemotwo.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/8.
 */
public class ChatFragment extends Fragment {

    GridView gv;
    MyBaseAdapter adapter;
    int [] res = {R.mipmap.ee_1, R.mipmap.ee_2,
            R.mipmap.ee_3, R.mipmap.ee_4, R.mipmap.ee_5, R.mipmap.ee_6,
            R.mipmap.ee_7, R.mipmap.ee_8, R.mipmap.ee_9,
            R.mipmap.ee_10, R.mipmap.ee_11, R.mipmap.ee_12,
            R.mipmap.ee_13, R.mipmap.ee_14, R.mipmap.ee_15,
            R.mipmap.ee_16, R.mipmap.ee_17, R.mipmap.ee_18,
            R.mipmap.ee_19, R.mipmap.ee_20, R.mipmap.ee_21,
            R.mipmap.ee_22, R.mipmap.ee_23, R.mipmap.ee_24,
            R.mipmap.ee_25, R.mipmap.ee_26, R.mipmap.ee_27,
            R.mipmap.ee_28, R.mipmap.ee_29, R.mipmap.ee_30,
            R.mipmap.ee_31, R.mipmap.ee_32, R.mipmap.ee_33,
            R.mipmap.ee_34, R.mipmap.ee_35,
    };
    public static List<Integer> gvList =new ArrayList<>();

    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        gvList.clear();
        for (int i = 0; i <res.length ; i++) {
            gvList.add(res[i]);
        }

        View view = View.inflate(getActivity(), R.layout.chat_fragment, null);
        gv = (GridView) view.findViewById(R.id.chat_frag_gv);

        adapter = new MyBaseAdapter(getActivity(),gvList);
        gv.setAdapter(adapter);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    ChatActivity.inputtext.append(ImageUtils.getSpanableString(getActivity(), EmoUtils.face_name[i]));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        return view;
    }


}