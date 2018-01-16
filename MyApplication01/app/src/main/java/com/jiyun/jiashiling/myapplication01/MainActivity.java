package com.jiyun.jiashiling.myapplication01;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiyun.jiashiling.myapplication01.adapters.SubListAdapter;
import com.jiyun.jiashiling.myapplication01.beans.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IndexView.onWordsChangeListener {

    private ListView list;
    private IndexView word;
    private TextView tv;
    private List<Person> lists = new ArrayList<>();;
    Handler handler = new Handler();
    private SubListAdapter subListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        list = (ListView) findViewById(R.id.list);
        word = (IndexView) findViewById(R.id.words);
        tv = (TextView) findViewById(R.id.tv);
        //索引列表监听
        word.setOnWordsChangeListener(this);
        subListAdapter = new SubListAdapter(MainActivity.this,lists);
        list.setAdapter(subListAdapter);
        initData();


        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                //当滑动列表的时候，更新右侧字母列表的选中状态
                word.setTouchIndex(lists.get(i).getHeaderWord());
            }
        });
    }


    private void initData() {
        lists.add(new Person("AA1","a","A"));
        lists.add(new Person("AA2","a","A"));
        lists.add(new Person("AA3","a","A"));
        lists.add(new Person("BB1","b","B"));
        lists.add(new Person("BB2","b2","B"));
        lists.add(new Person("BB3","b3","B"));
        lists.add(new Person("草泥马","caonima","C"));
        lists.add(new Person("樊晨辉","fanchenhui","F"));
        lists.add(new Person("逯择江","luzejiang","L"));
        lists.add(new Person("张月雷","zhangyuelei","Z"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("贾世玲","jiashiling","J"));
        lists.add(new Person("HH1","h1","H"));
        lists.add(new Person("HH2","h2","H"));

        lists.add(new Person("胡歌","huge","H"));
        lists.add(new Person("黄晓明","huangxiaoming","H"));
        lists.add(new Person("贾乃亮","jianailiang","J"));
        lists.add(new Person("赵又廷","zhaoyouting","Z"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("吴京","wujing","w"));
        lists.add(new Person("pp1","pp1","P"));
        lists.add(new Person("pp2","pp2","P"));
        lists.add(new Person("pp3","pp3","P"));
        lists.add(new Person("pp4","pp4","P"));
        lists.add(new Person("NN1","nn1","N"));
        lists.add(new Person("NN2","nn2","N"));
        lists.add(new Person("NN3","nn3","N"));
        lists.add(new Person("NN4","nn4","N"));
        lists.add(new Person("NN5","nn5","N"));
        lists.add(new Person("MM1","mm1","M"));
        lists.add(new Person("MM2","mm2","M"));
        lists.add(new Person("MM3","mm3","M"));
        lists.add(new Person("MM4","mm4","M"));
        lists.add(new Person("MM5","mm5","M"));
        lists.add(new Person("UU1","uu1","U"));
        lists.add(new Person("UU2","uu2","U"));
        lists.add(new Person("UU3","uu3","U"));
        lists.add(new Person("UU4","uu4","U"));
        lists.add(new Person("UU5","uu5","U"));
        lists.add(new Person("VV1","vv1","V"));
        lists.add(new Person("VV2","vv2","V"));
        lists.add(new Person("VV3","vv3","V"));
        lists.add(new Person("VV4","vv4","V"));
        lists.add(new Person("VV5","vv5","V"));


        lists.add(new Person("杨幂","yangmi","Y"));
        lists.add(new Person("杨幂","yangmi","Y"));
        lists.add(new Person("杨幂","yangmi","Y"));
        lists.add(new Person("杨幂","yangmi","Y"));
        lists.add(new Person("杨幂","yangmi","Y"));
        lists.add(new Person("唐嫣","tangyan","T"));
        lists.add(new Person("唐嫣","tangyan","T"));
        lists.add(new Person("唐嫣","tangyan","T"));
        lists.add(new Person("唐嫣","tangyan","T"));
        lists.add(new Person("唐嫣","tangyan","T"));
        lists.add(new Person("林允儿","linyuner","L"));
        lists.add(new Person("林允儿","linyuner","L"));
        lists.add(new Person("林允儿","linyuner","L"));
        lists.add(new Person("林允儿","linyuner","L"));
        lists.add(new Person("赵丽颖","zhaoliying","Z"));
        lists.add(new Person("赵丽颖","zhaoliying","Z"));
        lists.add(new Person("赵丽颖","zhaoliying","Z"));
        lists.add(new Person("赵丽颖","zhaoliying","Z"));
        lists.add(new Person("赵丽颖","zhaoliying","Z"));
        lists.add(new Person("赵丽颖","zhaoliying","Z"));
        lists.add(new Person("刘诗诗","liushishi","L"));
        lists.add(new Person("刘亦菲","liuyifei","L"));
        lists.add(new Person("刘亦菲","liuyifei","L"));
        lists.add(new Person("刘亦菲","liuyifei","L"));
        lists.add(new Person("刘亦菲","liuyifei","L"));
        lists.add(new Person("刘亦菲","liuyifei","L"));
        lists.add(new Person("迪丽热巴","dilierba","D"));
        lists.add(new Person("迪丽热巴","dilierba","D"));
        lists.add(new Person("迪丽热巴","dilierba","D"));
        lists.add(new Person("迪丽热巴","dilierba","D"));
        lists.add(new Person("迪丽热巴","dilierba","D"));
        lists.add(new Person("古力娜扎","gulinazhi","G"));
        lists.add(new Person("关晓彤","guanxiaotong","G"));
        lists.add(new Person("关晓彤","guanxiaotong","G"));
        lists.add(new Person("关晓彤","guanxiaotong","G"));

        //对集合排序
        Collections.sort(lists, new Comparator<Person>() {
        @Override
        public int compare(Person person1, Person person2) {
            //根据拼音进行排序
            return person1.getPinyin().compareTo(person2.getPinyin());
        }
    });

}

    //手指按下字母改变监听回调
    @Override
    public void wordsChange(String words) {
        updateWord(words);
        updateListView(words);
    }

    /**
     * 更新中央的字母提示
     * @param words 首字母
     */
    private void updateWord(String words) {
        tv.setText(words);
        tv.setVisibility(View.VISIBLE);
        //清空之前的所有消息
        handler.removeCallbacksAndMessages(null);
        //500ms后让tv隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setVisibility(View.GONE);
            }
        }, 500);
    }


    /**
     * @param words 首字母
     */
    private void updateListView(String words) {
        for (int i = 0; i < lists.size(); i++) {
            String headerWord = lists.get(i).getHeaderWord();
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words.equals(headerWord)) {
                //将列表选中哪一个
                list.setSelection(i);
                //找到开头的一个即可
                return;
            }
        }
    }
}
