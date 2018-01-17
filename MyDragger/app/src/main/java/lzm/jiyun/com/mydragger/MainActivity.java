package lzm.jiyun.com.mydragger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Inject
    public Text text;
    @InjectView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //在module里面不用传递参数
        DaggerComponents.create().inject(this);
        //在module里面传递参数
//      DaggerComponents.builder().netModule(new NetModule()).build().inject(this);

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        //在MainActivity没有创建Text的对象,通过注解调用方法
        text.setText(this);
    }
}
