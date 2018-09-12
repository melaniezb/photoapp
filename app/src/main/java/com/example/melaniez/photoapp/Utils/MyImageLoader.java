package com.example.melaniez.photoapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.melaniez.photoapp.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class MyImageLoader {
    private final static int defaultImage = R.drawable.ic_profile;
    private Context mContext;

    public MyImageLoader(Context context){
        mContext = context;
    }

    public ImageLoaderConfiguration getConfig() {
        DisplayImageOptions defaultDisplay = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        ImageLoaderConfiguration defaultConfig = new ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(defaultDisplay)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024)
                .build();
        return defaultConfig;
    }

    /**
     * this method can be used to set images that are static.
     * It can't be used if the images are being changed in the fragment/activity.
     * Or if they are being set in a list or in a grid.
     * @param imgUrl
     * @param imageView
     * @param progressBar
     * @param append
     */
    public static void setImage(String imgUrl, ImageView imageView, final ProgressBar progressBar, String append) {
        //append - different file types that the image loader can take
        //e.g. "http://" + "site.com/image.png"             (from web)
        //     "file://" + "/mnt/sdcard/image.png"          (from SD card)
        //     "file://" + "/mnt/sdcard/video.mp4"          (from SD card video thumbnail)
        //  "content://" + "media/external/images/media/13" (from content provider)
        //  "content://" + "media/external/video/media/13"  (from content provider video thumbnail)
        //   "assets://" + "image.png"                      (from assets)
        // "drawable://" + R.drawable.img                   (from drawables non-9patch images)
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(append + imgUrl, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
            }
        });
    }
}
