package com.example.wsj.myhuanxindemotwo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.wsj.myhuanxindemotwo.R;
import com.example.wsj.myhuanxindemotwo.adapter.MyBigBaseAdapter;
import com.example.wsj.myhuanxindemotwo.ui.EaseBaiduMapActivity;
import com.example.wsj.myhuanxindemotwo.ui.VideoActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/9.
 */
public class ChatAddFrafment extends Fragment {


    public static int a;
    GridView gv;
    MyBigBaseAdapter adapter;
    int[] res = {
            R.mipmap.ease_chat_takepic_normal,
            R.mipmap.ease_chat_image_normal,
            R.mipmap.ease_chat_location_normal,
            R.mipmap.em_chat_video_normal,
            R.mipmap.em_chat_file_normal,
            R.mipmap.em_chat_voice_call_normal,
            R.mipmap.em_chat_video_call_normal,
            R.mipmap.em_chat_video_normal
    };
    public static List<Integer> gvList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gvList.clear();
        for (int i = 0; i < res.length; i++) {
            gvList.add(res[i]);
        }

        View view = View.inflate(getActivity(), R.layout.chat_frag_big, null);
        gv = (GridView) view.findViewById(R.id.chat_frag_big_gv);

        adapter = new MyBigBaseAdapter(getActivity(), gvList);
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 下面这句指定调用相机拍照后的照片存储的路径
                    Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"image.jpg"));
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    getActivity().startActivityForResult(cameraIntent, 1);
                }else if(i == 1){
                    Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                    albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    getActivity().startActivityForResult(albumIntent, 2);
                }else if(i == 2){
                    Intent intent = new Intent(getActivity(), EaseBaiduMapActivity.class);
                    getActivity().startActivityForResult(intent,3);

                }else if(i == 3){
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    getActivity().startActivityForResult(intent,4);
                }

            }
        });

        return view;
    }





}
