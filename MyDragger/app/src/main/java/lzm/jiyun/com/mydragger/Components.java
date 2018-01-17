package lzm.jiyun.com.mydragger;

import dagger.Component;

/**
 * Created by lenovo on 2018/1/14.
 */
//连接Module
@Component(modules = {NetModule.class})
public interface Components {
    //连接MainActivity
    void inject(MainActivity mainActivity);
}
