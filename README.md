# ImageLoaderFramework
- **打造统一的图片加载框架，融合Glide，Fresco**，这两个底层包可随时相互替换，而无需大幅修改业务代码


- 图片加载模块作为手机常用的一个重要模块，我们需要保证对它有完全的控制力，以适应产品随时变化的需求，因此我们就需要整合自己的图片加载框架，将它和业务代码分离，不过分依赖哪一个包，保证必要时可以替换。

- 具体如何打造统一加载框架请参考这两篇文章《[封装并实现统一的图片加载架构](https://juejin.im/post/58b280b92f301e0068078669)》,《[项目重构之路——Fresco非入侵式替换Glide](https://juejin.im/post/592c319ea0bb9f005706a963)》

- 图片加载统一调用接口
```
    showImage(@NonNull ImageLoaderOptions options);
```

> 该接口的具体实现Glide和Fresco各有不同

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


- 修复图片加载时，图片隐藏和显示的问题，具体用法如下：
```
   /**
    * view : Imageview
    * visiable : {View.VISIBLE,View.INVISIBLE,View.GONE}
    */
    void hideImage(@NonNull View view,int visiable);
```




