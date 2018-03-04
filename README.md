# ImageLoaderFramework
- **打造统一的图片加载框架，融合Glide，Fresco，一套API兼容两种加载方式**
- 两个底层包Glide，Fresco可随时相互替换，而无需大幅修改业务代码


- 图片加载模块作为手机常用的一个重要模块，我们需要保证对它有完全的控制力，以适应产品随时变化的需求，因此我们就需要整合自己的图片加载框架，将它和业务代码分离，不过分依赖哪一个包，保证必要时可以替换。

- 具体如何打造统一加载框架请参考这两篇文章《[封装并实现统一的图片加载架构](https://juejin.im/post/58b280b92f301e0068078669)》,《[项目重构之路——Fresco非入侵式替换Glide](https://juejin.im/post/592c319ea0bb9f005706a963)》

- 使用方式：
```
    // 下面两个依赖包可选，根据需求二选一即可，
    compile 'com.ladingwu.library:fresco:0.0.4'
    compile 'com.ladingwu.library:glide:0.0.4'
    // 这个是必须的
    compile "com.ladingwu.library:imageloader-framework:0.0.4"
    
```

- 初始化
```
        ImageLoaderConfig config = new ImageLoaderConfig.Builder(LoaderEnum.GLIDE,new GlideImageLocader())
                .maxMemory(40*1024*1024L)  // 配置内存缓存，单位为Byte
                .build();
        ImageLoaderManager.getInstance().init(this,config);
        
```
初始化代码需要在Application中完成。


- 图片加载统一调用接口
```
    showImage(@NonNull ImageLoaderOptions options);
```

> 该接口的具体实现Glide和Fresco各有不同


- 使用范例：
```
       // 加载圆角图片
   ImageLoaderOptions op=new ImageLoaderOptions.Builder(img1,url).imageRadiusDp(12).build();
   ImageLoaderManager.getInstance().showImage(op);
                
                
                
   ImageLoaderOptions options=new ImageLoaderOptions.Builder(img2,url)
                                                    .blurImage(true)   // 高斯模糊    
                                                    .blurValue(35)   //高斯模糊程度
                                                    .isCircle()   // 圆图  
                                                     .placeholder(R.mipmap.ic_launcher)// 占位图
                                                     .build(); 
                                                                  
        // 如果项目同时使用了Fresco和Glide,可以指定特定的加载框架加载图片                                      
  ImageLoaderManager.getInstance().showImage(options, LoaderEnum.GLIDE);  // 选择通过Glide加载图片
                 
                 
```

## 2018-03-04 更新

- 添加了对Fresco内存缓存的配置功能(Glide暂时采用默认配置)。



## 2018-02-01 更新

- 添加了对于Glide4.x版本的支持
- 添加了圆图，圆角，高斯模糊的支持，并且保证Glide和Fresco的效果大体相同


## 2017-8-12 更新

- 添加图片暂停加载和恢复功能，方便优化处理

```
    // 暂停加载
    void pause(Context context);
    // 恢复加载
    void resume(Context context);
```






