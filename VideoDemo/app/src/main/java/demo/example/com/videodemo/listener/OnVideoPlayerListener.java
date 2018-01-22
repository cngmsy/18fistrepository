package demo.example.com.videodemo.listener;

/**
 * 视频播放状态监听
 */
public interface OnVideoPlayerListener {
    void onVideoPrepared();

    void onVideoCompletion();

    void onVideoError(int i, String error);

    void onBufferingUpdate();

    void onVideoStopped();

    void onVideoPause();

    void onVideoThumbPause();

    void onVideoThumbStart();

    void onVideoPlayingPro(long currentPosition, long mDuration, int mPlayStatus);
}
