package com.example.wsj.myhuanxindemotwo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.wsj.myhuanxindemotwo.R;
import com.example.wsj.myhuanxindemotwo.adapter.MyVideoAdapter;
import com.example.wsj.myhuanxindemotwo.bean.VideoInfo;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    private Button btn;
    private ListView lv;
    private MyVideoAdapter adapter;
    public static List<VideoInfo> allVideoList = null;// 视频信息集合
    private File f;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        lv = (ListView) findViewById(R.id.video_lv);
        btn = (Button) findViewById(R.id.video_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"video.mp4"));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent,10);
            }
        });

        allVideoList = new ArrayList<VideoInfo>();


        getVideoFile(allVideoList, Environment.getExternalStorageDirectory());

        adapter = new MyVideoAdapter(allVideoList,this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                intent.putExtra("path",allVideoList.get(i).path);
                intent.putExtra("pic",allVideoList.get(i).PicNamefile);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void getVideoFile(final List<VideoInfo> list, File file) {

        file.listFiles(new FileFilter() {

            public boolean accept(File file) {
                // sdCard找到视频名称
                String name = file.getName();

                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4")
                            || name.equalsIgnoreCase(".3gp")
                            || name.equalsIgnoreCase(".wmv")
                            || name.equalsIgnoreCase(".ts")
                            || name.equalsIgnoreCase(".rmvb")
                            || name.equalsIgnoreCase(".mov")
                            || name.equalsIgnoreCase(".m4v")
                            || name.equalsIgnoreCase(".avi")
                            || name.equalsIgnoreCase(".m3u8")
                            || name.equalsIgnoreCase(".3gpp")
                            || name.equalsIgnoreCase(".3gpp2")
                            || name.equalsIgnoreCase(".mkv")
                            || name.equalsIgnoreCase(".flv")
                            || name.equalsIgnoreCase(".divx")
                            || name.equalsIgnoreCase(".f4v")
                            || name.equalsIgnoreCase(".rm")
                            || name.equalsIgnoreCase(".asf")
                            || name.equalsIgnoreCase(".ram")
                            || name.equalsIgnoreCase(".mpg")
                            || name.equalsIgnoreCase(".v8")
                            || name.equalsIgnoreCase(".swf")
                            || name.equalsIgnoreCase(".m2v")
                            || name.equalsIgnoreCase(".asx")
                            || name.equalsIgnoreCase(".ra")
                            || name.equalsIgnoreCase(".ndivx")
                            || name.equalsIgnoreCase(".xvid")) {
                        VideoInfo vi = new VideoInfo();
                        vi.DisplayNamefile = name ;
                        vi.path = file.getAbsolutePath();
                        vi.bm  = getVideoThumbnail(vi.path);
                        Bitmap bm  = getVideoThumbnail(vi.path);
                        saveBitmap(bm);
                        vi.PicNamefile = path + "/AAAAAAAA"+".png";
                        list.add(vi);
                        return true;
                    }
                } else if (file.isDirectory()) {
                    getVideoFile(list, file);
                }
                return false;
            }
        });

    }

    public static Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media =new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }
    public void saveBitmap(Bitmap bm) {
        path= Environment.getExternalStorageDirectory().toString();
        File file=new File(path, "AAAAAAAA"+".png");
        try {
            FileOutputStream out=new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode == 10){
                Intent intent = getIntent();
                String video = Environment.getExternalStorageDirectory() + "/video.mp4";
                intent.putExtra("videoPath",video);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
