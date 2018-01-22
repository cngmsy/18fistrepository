package demo.example.com.videodemo;

import android.app.Application;
import android.content.Context;

public class VideoApp extends Application {

    private static VideoApp mInstantce;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstantce = this;
    }

    public static Context getAppContext() {
        return mInstantce.getApplicationContext();
    }
}
