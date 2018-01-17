package lzm.jiyun.com.mydragger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2018/1/14.
 */
//此类用于创建实例
@Module
public class NetModule {
    @Provides
    public Text getText(){
        return new Text();
    }
}
