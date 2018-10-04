package com.sleepwind.Base;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BaseApplication extends Application {

    static DisplayMetrics displayMetrics;

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader(this);
        displayMetrics = getResources().getDisplayMetrics();
    }

    private void initImageLoader(Context context) {

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(false).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static int getScreenWidth() {
        return displayMetrics.widthPixels;
    }
}
