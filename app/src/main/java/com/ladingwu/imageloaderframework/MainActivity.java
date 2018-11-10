package com.ladingwu.imageloaderframework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lasingwu.baselibrary.ImageLoaderManager;
import com.lasingwu.baselibrary.ImageLoaderOptions;
import com.lasingwu.baselibrary.LoaderEnum;
import com.lasingwu.baselibrary.OnLoaderProgressCallback;


public class MainActivity extends AppCompatActivity {

    private ImageView img1,img2;
    private String url="http://img1.imgtn.bdimg.com/it/u=679805784,3150507797&fm=214&gp=0.jpg";
//    private String urlGif="http://img2.imgtn.bdimg.com/it/u=2938769139,1872984641&fm=214&gp=0.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1= (ImageView) findViewById(R.id.img_1);
        img2= (ImageView) findViewById(R.id.img_2);
        final Button btn1= (Button) findViewById(R.id.btn1);
        final Button btn2= (Button) findViewById(R.id.btn2);
//        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        binding.setUser(new JavaBean());
//        img1=binding.img1;
//        img2=binding.img2;
        img1.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageLoaderOptions op=new ImageLoaderOptions.Builder(img1,url).setOnLoaderProgressCallback(new OnLoaderProgressCallback() {
                    @Override
                    public void onProgress(int progress) {
                        Log.w("===== ","=====================>"+progress);
                    }
                }).imageRadiusDp(12).build();
                ImageLoaderManager.getInstance().showImage(op);


            }
        },2000);

        ImageLoaderOptions options=new ImageLoaderOptions.Builder(img2,url)
                .blurImage(true)
                .blurValue(35)
                .isCircle()
                .placeholder(R.mipmap.ic_launcher).build();
        //  LoaderEnum类型如果指定的话，一定要和application 中初始化指定的一致，不然找不到相应的加载库而无法加载
        ImageLoaderManager.getInstance().showImage(options, LoaderEnum.FRESCO);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PhotoListActivity.class));

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PhotoListActivity.class));

            }
        });

        // 测试取消加载和恢复加载
        // 暂停加载
        ImageLoaderManager.getInstance().pause(this);
        img1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 恢复加载
              ImageLoaderManager.getInstance().resume(MainActivity.this);
            }
        },2000);
    }


}
