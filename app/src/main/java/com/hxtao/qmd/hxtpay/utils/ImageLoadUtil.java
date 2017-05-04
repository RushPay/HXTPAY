
package com.hxtao.qmd.hxtpay.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.hxtao.qmd.hxtpay.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 图片加载器
 *
 * @author tu
 */
public class ImageLoadUtil {
    public static ImageLoader imageLoader = ImageLoader.getInstance();

    /**
     * @方法 初始化图片加载器
     */
    public static void initImageLoader(Context context) {
        // 创建默认的DisplayImageOptions DisplayImageOptions.createSimple()

        // 此配置调整为自定义，可以调整每一个选项
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .defaultDisplayImageOptions(getDefaultOptions(R.mipmap.icon_photo_normal))
                .threadPoolSize(4)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .memoryCache(new WeakMemoryCache())
                .diskCacheFileCount(200)
                .memoryCacheSizePercentage(2)
                //.writeDebugLogs()
                //.imageDownloader()
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .build();
        // 使用配置初始化ImageLoader
        ImageLoader.getInstance().init(config);
    }

    /**
     * 加载常规图片
     *
     * @param url
     * @param imageAware
     */
    public static void disPlayImage(String url, ImageView imageAware) {
        imageLoader.displayImage(url, imageAware, new AnimateFirstDisplayListener());
    }
    /**
     * 加载常规图片 传入指定占位图
     *
     * @param url
     * @param imageAware
     * @param defaultImgRes 占位图
     */
    public static void disPlayImage(String url, ImageView imageAware,int defaultImgRes) {
        imageLoader.displayImage(url, imageAware, getDefaultOptions(defaultImgRes),new AnimateFirstDisplayListener());
    }

    /**
     * 加载带有圆角的矩形图片
     *
     * @param url
     * @param imageAware
     */
    public static void disPlayRectangleImage(String url, ImageView imageAware) {
        imageLoader.displayImage(url, imageAware, options);
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param imageAware
     */
    public static void disPlayRoundImage(String url, ImageView imageAware) {
        imageLoader.displayImage(url, imageAware, getRoundImageOptions(), new AnimateFirstDisplayListener(), null);
    }

    /**
     * 加载sd卡图片  (imageview要给固定的宽高 这样图片才能显示)
     *
     * @param imagePath
     * @param imageAware
     */
    public static void disPlaySdCard(String imagePath, ImageView imageAware) {
        //String imagePath = "/mnt/sdcard/image.png";
/*		//图片来源于Content provider  
        String contentprividerUrl = "content://media/external/audio/albumart/13";          
        //图片来源于assets  
        String assetsUrl = Scheme.ASSETS.wrap("image.png");           
        //图片来源于  
        String drawableUrl = Scheme.DRAWABLE.wrap("R.drawable.image"); */ 
        
		
		/*String imageUri = "http://site.com/image.png"; // 网络图片  
        String imageUri = "file:///mnt/sdcard/image.png"; //SD卡图片
		String imageUri = "content://media/external/audio/albumart/13"; // 媒体文件夹  
		String imageUri = "assets://image.png"; // assets  
		String imageUri = "drawable://" + R.drawable.image; //  drawable文件 
*/
        String imageUrl = Scheme.FILE.wrap(imagePath);
        imageLoader.displayImage(imageUrl, imageAware, getSdCardOptions());
    }

    /**
     * 默认选项
     *
     * @return
     */
    private static DisplayImageOptions getDefaultOptions(int defaultImgRes) {
        // 创建用于默认的通用选项
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false).imageScaleType(ImageScaleType.EXACTLY)
                // 设置正在加载图片
                .showImageOnLoading(defaultImgRes)
                // 设置请求路径为空的图片
                .showImageForEmptyUri(defaultImgRes)
                // 设置加载失败图片
                .showImageOnFail(defaultImgRes)
                // 是否缓存之本地
                .cacheInMemory(true) //启用内存缓存
                .cacheOnDisk(true)   //启用外存缓存
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        return defaultOptions;
    }

    /**
     * 图片加载器显示圆形图片第三个参数
     *
     * @return
     */
    private static DisplayImageOptions getRoundImageOptions() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_head)            //加载图片时的图片
                .showImageForEmptyUri(R.mipmap.default_head)         //没有图片资源时的默认图片
                .showImageOnFail(R.mipmap.default_head)              //加载失败时的图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .displayer(new RoundedBitmapDisplayer(180))         //设置显示风格这里是圆角矩形
                .build();
        return options;
    }

/*	listView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));  
    gridView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));  
	第一个参数就是我们的图片加载对象ImageLoader, 第二个是控制是否在滑动过程中暂停加载图片，如果需要暂停传true就行了
	，第三个参数控制猛的滑动界面的时候图片是否加载
	显示图片的配置  */

    /**
     * 显示sd卡图片
     *
     * @return
     */
    private static DisplayImageOptions getSdCardOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.icon_photo_normal)
                .showImageOnFail(R.mipmap.icon_photo_normal)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }

    /**
     * 图片加载第一次显示监听器
     *
     * @author Administrator
     */
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 800);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .displayer(new RoundedBitmapDisplayer(10))
            .showStubImage(R.mipmap.icon_photo_normal)
            .showImageForEmptyUri(R.mipmap.icon_photo_normal)
            .showImageOnFail(R.mipmap.icon_photo_normal)
            .cacheInMemory(true).cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
}

