package com.example.ladingwu.imageloaderframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ladingwu.imageloaderframework.imageload.ImageLoaderManager;


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
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(img1,url));
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(img2,url));
    }
}
