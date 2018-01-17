package com.example.wsj.myhuanxindemotwo.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wsj.myhuanxindemotwo.R;
import com.example.wsj.myhuanxindemotwo.bean.VideoInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by user on 2016/7/18.
 */
public class MyVideoAdapter extends BaseAdapter {

    public static int a;
    List<VideoInfo> list;
    Context context;
    private MediaPlayer player;

    public MyVideoAdapter(List<VideoInfo> list, Context context) {
        this.list = list;
        this.context = context;
        player = new MediaPlayer();
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

        a = i;
        view = View.inflate(context, R.layout.video_item,null);
        TextView tv = (TextView) view.findViewById(R.id.video_item_tv);
        ImageView iv = (ImageView) view.findViewById(R.id.video_item_iv);
        iv.setImageBitmap(list.get(i).bm);

        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.setDataSource(list.get(i).path);
            tv.setText(list.get(i).path+"****"+ formatTime(player.getDuration()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    public static String formatTime(int time){
        //对毫秒进行时间的格式化
        if (time / 1000 % 60 < 10) {
            return time/1000/60+":0"+time/1000%60;
        }else{
            return time/1000/60+":"+time/1000%60;
        }

    }
}
