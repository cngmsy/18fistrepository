package com.example.wsj.myhuanxindemotwo.view;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by user on 2016/7/15.
 */
public class MediaManager {

    private static MediaPlayer mMediaPlayer;

    private static boolean isPause;

    public static void playSound(String filePath
            , MediaPlayer.OnCompletionListener onCompletionListener)
    {

        if(mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {

                    mMediaPlayer.reset();
                    return false;
                }
            });
        }else{
             mMediaPlayer.reset();
        }



        try {

            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void pause(){
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            isPause = true;
        }
    }

    public static void resume(){
        if (mMediaPlayer != null && isPause) {
            mMediaPlayer.start();
            isPause = false;
        }
    }

    public static void release(){
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}
