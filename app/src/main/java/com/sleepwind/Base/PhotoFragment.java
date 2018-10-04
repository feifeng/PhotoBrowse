package com.sleepwind.Base;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sleepwind.R;
import com.sleepwind.activity.BrowseActivity;

import java.util.ArrayList;
import java.util.List;

public class PhotoFragment extends Fragment {

    private BrowseActivity activity;
    private ImageView view;
    private ImageLoader imageLoader;
    private int index;
    private List<String> photoPathList = new ArrayList<String>();

    public static Fragment newFragment(int index, ArrayList<String> photoPathList) {

        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putStringArrayList("PHOTO_PATH", photoPathList);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BrowseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt("index");
        photoPathList = getArguments().getStringArrayList("PHOTO_PATH");
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (ImageView) inflater.inflate(R.layout.fragment_viewer, container, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.supportFinishAfterTransition();
            }
        });

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                getActivity().supportStartPostponedEnterTransition();
                return true;
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        String url = String.format(URLS.PHOTO_BIG, photoPathList.get(index));

        Glide.with(activity).load(url).asBitmap().dontAnimate().into(view);
    }

    public View getSharedElement() {
        return view;
    }
}
