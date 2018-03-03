package com.ladingwu.imageloaderframework;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzhao on 2018/3/3.
 */

public class PhotoBean {
    public String url;

    public static PhotoBean createInstance(String url){
        PhotoBean bean =new PhotoBean();
        bean.url=url;
        return bean;
    }

    public static List<PhotoBean> createInstances(@NonNull  List<String> urls){
        List<PhotoBean> list =new ArrayList<>();
        for (String url : urls) {
            PhotoBean bean =new PhotoBean();
            bean.url=url;
            list.add(bean);
        }
        return list;
    }
}
