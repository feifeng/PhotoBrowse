package com.sleepwind.activity;

import android.annotation.TargetApi;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sleepwind.Base.URLS;
import com.sleepwind.R;
import com.sleepwind.adapter.PhotoAdapter;
import com.sleepwind.decoration.PhotoItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView photoRecyclerView;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> photoPathList = new ArrayList<String>();
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initSharedElement();
    }

    private void initData() {
        for (int i = 1; i < 34; i++) {
            photoPathList.add(String.format(URLS.PHOTO_NAME, i));
        }
    }

    private void initView() {
        photoRecyclerView = findViewById(R.id.photoRecyclerView);
        photoRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        PhotoItemDecoration decoration = new PhotoItemDecoration();
        if (photoRecyclerView.getItemDecorationCount() == 0) {
            photoRecyclerView.addItemDecoration(decoration);
        }

        photoAdapter = new PhotoAdapter(this, photoPathList);
        photoRecyclerView.setAdapter(photoAdapter);
    }

    public void onItemClick(View view) {
        int position = photoRecyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(this, BrowseActivity.class);
        intent.putStringArrayListExtra("PHOTO_PATH", photoPathList);
        intent.putExtra("index", position);

        ImageView imageView = view.findViewById(R.id.photoImageView);

        if (Build.VERSION.SDK_INT >= 22) {
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, photoPathList.get(position));
            startActivity(intent, compat.toBundle());
        }else {
            startActivity(intent);
        }
    }

    @TargetApi(21)
    private void initSharedElement() {
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (bundle != null) {
                    int i = bundle.getInt("index", 0);
                    sharedElements.clear();
                    names.clear();

                    View view = photoRecyclerView.getChildAt(i);
                    ImageView imageView = view.findViewById(R.id.photoImageView);

                    sharedElements.put(photoPathList.get(i), imageView);
                    bundle = null;
                }
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        bundle = new Bundle(data.getExtras());
    }
}
