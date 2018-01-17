package com.example.wsj.myhuanxindemotwo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wsj.myhuanxindemotwo.R;
import com.example.wsj.myhuanxindemotwo.adapter.ChatFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/9.
 */
public class ChatBQFragment extends Fragment {


    ViewPager chat_vp;
    private List<Fragment> vpList = new ArrayList<Fragment>();
    private ChatFragmentPagerAdapter VpAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.chat_bq_frag,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        chat_vp = (ViewPager) view.findViewById(R.id.chat_vp);
        ChatFragment fragment = new ChatFragment();
        vpList.add(fragment);
        ChatFragmentBig fragment1 = new ChatFragmentBig();
        vpList.add(fragment1);
        VpAdapter = new ChatFragmentPagerAdapter(getChildFragmentManager(), vpList);
        chat_vp.setAdapter(VpAdapter);
    }


}
