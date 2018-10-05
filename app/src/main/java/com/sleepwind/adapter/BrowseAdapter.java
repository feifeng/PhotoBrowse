package com.sleepwind.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sleepwind.Base.URLS;
import com.sleepwind.R;

import java.util.ArrayList;

public class BrowseAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> photoPathList = new ArrayList<String>();
    private ImageLoader imageLoader;
    private ImageView imageView;

    public BrowseAdapter(Context context, ArrayList<String> photoPathList) {
        this.context = context;
        this.photoPathList = photoPathList;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return photoPathList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        final ImageView imageView = (ImageView) View.inflate(context, R.layout.fragment_viewer, null);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)context).supportFinishAfterTransition();
            }
        });
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                ((AppCompatActivity)context).supportStartPostponedEnterTransition();
                return true;
            }
        });

        String photoURL = String.format(URLS.PHOTO, photoPathList.get(position));
//        imageLoader.displayImage(photoURL, imageView);

         Glide.with(context).load(photoURL).asBitmap().dontAnimate().into(imageView);

        container.addView(imageView);
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        imageView = (ImageView) object;
    }

    public ImageView getPrimaryItem() {
        return imageView;
    }
}
