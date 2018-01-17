package com.example.wsj.myhuanxindemotwo.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wsj.myhuanxindemotwo.R;
import com.example.wsj.myhuanxindemotwo.ui.EaseBaiduMapActivity;
import com.example.wsj.myhuanxindemotwo.ui.VideoActivity;
import com.example.wsj.myhuanxindemotwo.utils.ImageUtils;
import com.example.wsj.myhuanxindemotwo.utils.ImgTools;
import com.example.wsj.myhuanxindemotwo.view.MediaManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by user on 2016/7/7.
 */
public class ChatAdapter extends BaseAdapter {
    Context context;
    List<EMMessage> list;
    private int mMinItenWidth;
    private int mMaxItenWidth;


    public ChatAdapter(Context context, List<EMMessage> list) {
        this.context = context;
        this.list = list;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mMaxItenWidth = (int) (outMetrics.widthPixels * 0.7f);
        mMinItenWidth = (int) (outMetrics.widthPixels * 0.15f);

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
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final EMMessage message = list.get(i);
        if (list.get(i).getFrom().equals(EMClient.getInstance().getCurrentUser())) {
            view = View.inflate(context, R.layout.item_chat_right, null);
            TextView tv = (TextView) view.findViewById(R.id.item_chat_right_name);
            ImageView iv = (ImageView) view.findViewById(R.id.item_chat_right_iv);
            TextView tvmap = (TextView) view.findViewById(R.id.item_tv_map);
            FrameLayout fl = (FrameLayout) view.findViewById(R.id.recorder_length);
            final View ivyuyin = view.findViewById(R.id.id_recorder_anim);
            TextView tvyuyin = (TextView) view.findViewById(R.id.id_recorder_time);
            String messagetext = list.get(i).getBody() + "";
            String s1 = messagetext.substring(5, messagetext.length() - 1);
            if (message.getBooleanAttribute("MESSAGE_ATTR_IS_BIG_EXPRESSION", false)) {
                tvyuyin.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
                Glide.with(context).load(Integer.parseInt(s1)).placeholder(R.mipmap.ease_default_expression).into(iv);
                return view;
            } else if (message.getType() == EMMessage.Type.IMAGE) {
                tvyuyin.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
                EMImageMessageBody imgPath = (EMImageMessageBody) message.getBody();
                ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
                ImgTools.getInstance().getImgFromNetByUrl(imgPath.getRemoteUrl(), iv, R.mipmap.ease_default_image);
                return view;
            } else if (message.getType() == EMMessage.Type.LOCATION) {
                tvyuyin.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.GONE);
                tvmap.setVisibility(View.VISIBLE);
                tvmap.setText(((EMLocationMessageBody) message.getBody()).getAddress());
                tvmap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, EaseBaiduMapActivity.class);
                        intent.putExtra("longitude",((EMLocationMessageBody) message.getBody()).getLongitude());
                        intent.putExtra("latitude",((EMLocationMessageBody) message.getBody()).getLatitude());
                        context.startActivity(intent);
                    }
                });

                return view;
            }else if(message.getType() == EMMessage.Type.VOICE){
                final EMVoiceMessageBody body = (EMVoiceMessageBody) message.getBody();
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.GONE);
                tvmap.setVisibility(View.GONE);
                fl.setVisibility(View.VISIBLE);
                tvyuyin.setText(Math.round(body.getLength()) + "\"");
                ViewGroup.LayoutParams lp = fl.getLayoutParams();
                lp.width = (int) (mMinItenWidth +(mMaxItenWidth / 60f * body.getLength()));

                fl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ivyuyin != null) {
                            ivyuyin.setBackgroundResource(R.mipmap.adj);

                        }


                        AnimationDrawable drawable=new AnimationDrawable();
                        for (int i = 1; i < 4; i++) {

                            int id=context.getResources().getIdentifier("v_anim"+i, "drawable", context.getPackageName());
                            Drawable drawable2=context.getResources().getDrawable(id);
                            drawable.addFrame(drawable2, 300);
                        }
                        drawable.setOneShot(false);
                        drawable.setEnterFadeDuration(body.getLength());
                        ivyuyin.setBackground(drawable);
                        drawable.start();


                        //播放音频
                        MediaManager.playSound(body .getRemoteUrl()

                                , new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {


                                        ivyuyin.setBackgroundResource(R.mipmap.adj);

                                    }
                                });






                    }
                });

                return view;
            }else if(message.getType() == EMMessage.Type.VIDEO) {

                tvyuyin.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
                final EMVideoMessageBody videoPath = (EMVideoMessageBody) message.getBody();
                if (videoPath.getVideoFileLength() == 11) {
                }


                if (VideoActivity.allVideoList != null && VideoActivity.allVideoList.size() > 0) {
                    iv.setImageBitmap(VideoActivity.allVideoList.get(MyVideoAdapter.a).bm);
                    iv.setBackgroundResource(R.mipmap.ease_app_panel_video_icon);
                }else {
                    iv.setImageResource(R.mipmap.ease_app_panel_video_icon);
                }
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(videoPath.getRemoteUrl());
                        //调用系统自带的播放器
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Log.v("URI:::::::::", uri.toString());
                        intent.setDataAndType(uri, "video/mp4");
                        context.startActivity(intent);


                    }
                });
                return view;

            }


            else {
                tvyuyin.setVisibility(View.GONE);
                iv.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                tv.setText(ImageUtils.formatString(context, s1));
                return view;
            }


        } else {
            view = View.inflate(context, R.layout.item_chat_left, null);
            TextView tv = (TextView) view.findViewById(R.id.item_chat_left_name);
            ImageView iv = (ImageView) view.findViewById(R.id.item_chat_left_iv);
            TextView tvleftmap = (TextView) view.findViewById(R.id.item_chat_left_tv);

            FrameLayout fl = (FrameLayout) view.findViewById(R.id.recorder_length_left);
            final View ivyuyin = view.findViewById(R.id.id_recorder_anim_left);
            TextView tvyuyin = (TextView) view.findViewById(R.id.id_recorder_time_left);
            String messagetext = list.get(i).getBody() + "";
            String s1 = messagetext.substring(5, messagetext.length() - 1);

            if (message.getBooleanAttribute("MESSAGE_ATTR_IS_BIG_EXPRESSION", false)) {
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
                tvyuyin.setVisibility(View.GONE);
                ivyuyin.setVisibility(View.GONE);
                Glide.with(context).load(Integer.parseInt(s1)).placeholder(R.mipmap.ease_default_expression).into(iv);
                return view;
            } else if (message.getType() == EMMessage.Type.IMAGE) {
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
                tvyuyin.setVisibility(View.GONE);
                ivyuyin.setVisibility(View.GONE);
                EMImageMessageBody imgPath = (EMImageMessageBody) message.getBody();
                ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
                ImgTools.getInstance().getImgFromNetByUrl(imgPath.getRemoteUrl(), iv, R.mipmap.ease_default_image);
                return view;
            }else if (message.getType() == EMMessage.Type.LOCATION) {
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.GONE);
                tvyuyin.setVisibility(View.GONE);
                ivyuyin.setVisibility(View.GONE);
                tvleftmap.setVisibility(View.VISIBLE);
                tvleftmap.setText(((EMLocationMessageBody) message.getBody()).getAddress());
                tvleftmap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, EaseBaiduMapActivity.class);
                        intent.putExtra("longitude",((EMLocationMessageBody) message.getBody()).getLongitude());
                        intent.putExtra("latitude",((EMLocationMessageBody) message.getBody()).getLatitude());
                        context.startActivity(intent);
                    }
                });

                return view;
            }

            else if(message.getType() == EMMessage.Type.VOICE){
                final EMVoiceMessageBody body = (EMVoiceMessageBody) message.getBody();
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.GONE);
                tvleftmap.setVisibility(View.GONE);
                tvyuyin.setText(Math.round(body.getLength()) + "\"");
                ViewGroup.LayoutParams lp = fl.getLayoutParams();
                lp.width = (int) (mMinItenWidth +(mMaxItenWidth / 60f * body.getLength()));
                fl.setVisibility(View.VISIBLE);

                fl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ivyuyin != null) {
                            ivyuyin.setBackgroundResource(R.mipmap.adj);

                        }


                        AnimationDrawable drawable=new AnimationDrawable();
                        for (int i = 1; i < 4; i++) {

                            int id=context.getResources().getIdentifier("v_anim"+i, "drawable", context.getPackageName());
                            Drawable drawable2=context.getResources().getDrawable(id);
                            drawable.addFrame(drawable2, 300);
                        }
                        drawable.setOneShot(false);
                        drawable.setEnterFadeDuration(body.getLength());
                        ivyuyin.setBackground(drawable);
                        drawable.start();


                        //播放音频
                        MediaManager.playSound(body.getRemoteUrl()

                                , new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {


                                        ivyuyin.setBackgroundResource(R.mipmap.adj);

                                    }
                                });






                    }
                });

                return view;
            }
            else if(message.getType() == EMMessage.Type.VIDEO){

                tvyuyin.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
                final EMVideoMessageBody videoPath = (EMVideoMessageBody) message.getBody();
                Log.e("****************", videoPath.getThumbnailUrl());
//                ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
//                ImgTools.getInstance().getImgFromNetByUrl(videoPath.getThumbnailUrl(), iv, R.drawable.ease_default_image);
                iv.setImageResource(R.mipmap.ease_app_panel_video_icon);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(videoPath.getRemoteUrl());
                        //调用系统自带的播放器
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Log.v("URI:::::::::", uri.toString());
                        intent.setDataAndType(uri, "video/mp4");
                        context.startActivity(intent);


                    }
                });
                return view;

            }




            else {
                iv.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                tvyuyin.setVisibility(View.GONE);
                ivyuyin.setVisibility(View.GONE);
                tv.setText(ImageUtils.formatString(context, s1));
                return view;
            }
        }


    }



    public void setListener(List<EMMessage> list) {
        this.list = list;
        notifyDataSetChanged();
    }



//                ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
//                ImgTools.getInstance().getImgFromNetByUrl(videoPath.getThumbnailUrl(), iv, R.drawable.ease_default_image);

}
