# ImageLoaderFramework
- **Create a unified image loading framework, merge Glide, Fresco, a set of API compatible two loading methods**

- Two underlying packages, Glide, Fresco can be replaced at any time without major modification of the business code.

- The image loading module is an important module commonly used in mobile phones. We need to ensure that it has complete control over it to adapt to the changing needs of the product, so we need to integrate our image loading framework and separate it from the business code. Which package to rely on, to ensure that it can be replaced if necessary.

- For details on how to create a unified loading framework, please refer to the two articles "Encapsulating and Implementing a Unified Image Loading Architecture", "The Road to Project Reconstruction - Fresco Non-Invasive Replacement Glide".

How to use:
```
    // The following two dependencies are optional, and you can choose one according to your needs.
    compile 'com.ladingwu.library:fresco:0.0.8'
    compile 'com.ladingwu.library:glide:0.0.8'
    // This is a must
    compile "com.ladingwu.library:imageloader-framework:0.0.8"
    
```

- Initialization
```
        ImageLoaderConfig config = new ImageLoaderConfig.Builder(LoaderEnum.GLIDE,new GlideImageLocader())
                .maxMemory(40*1024*1024L)  // Configure the memory cache in units Byte
                .build();
        ImageLoaderManager.getInstance().init(this,config);
        
```
The initialization code needs to be done in the Application.


- Image loading unified call interface
```
    showImage(@NonNull ImageLoaderOptions options);
```

> The specific implementation of this interface is different from Glide and Fresco.


- Example：
```
       // Loading rounded corners
   ImageLoaderOptions op=new ImageLoaderOptions.Builder(img1,url).imageRadiusDp(12).build();
   ImageLoaderManager.getInstance().showImage(op);
                
                
                
   ImageLoaderOptions options=new ImageLoaderOptions.Builder(img2,url)
                                                    .blurImage(true)   // Gaussian blur
                                                    .blurValue(35)   // Gaussian blur
                                                    .isCircle()   // Circle chart  
                                                     .placeholder(R.mipmap.ic_launcher)// Place chart
                                                     .build(); 
                                                                  
        // If the project uses both Fresco and Glide, you can specify a specific loading frame to load the image.
  ImageLoaderManager.getInstance().showImage(options, LoaderEnum.GLIDE);  // Choose to load images via Glide
                 
                 
```

## 2018-03-04 Update

- Added configuration features for Fresco memory cache (Glide temporarily uses the default configuration).



## 2018-02-01 Update

- Added support for Glide 4.x version
- Added support for circle, fillet, Gaussian blur, and guaranteed that Glide and Fresco have the same effect


## 2017-8-12 Update

- Add image pause loading and recovery for easy optimization

```
    // Pause loading
    void pause(Context context);
    // Resume loading
    void resume(Context context);
```






