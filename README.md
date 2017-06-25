# ImageLoaderFramework
打造统一的图片加载框架，融合Glide，Fresco

图片加载模块作为手机常用的一个重要模块，我们需要保证对它有完全的控制力，以适应产品随时变化的需求，因此我们就需要整合自己的图片加载框架，不过分依赖哪一个包

- 图片加载统一调用
```
    showImage(@NonNull ImageLoaderOptions options);
```

> 具体实现Glide或者Fresco可以各有不同


