package com.sleepwind.activity;

import android.annotation.TargetApi;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sleepwind.R;
import com.sleepwind.adapter.BrowseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BrowseActivity extends AppCompatActivity {

    private ViewPager browseViewPager;
    private BrowseAdapter browseAdapter;
    private ArrayList<String> photoPathList = new ArrayList<String>();
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        supportPostponeEnterTransition();

        initView();
        initSharedElement();
        initListener();
    }

    private void initView() {
        photoPathList = getIntent().getStringArrayListExtra("PHOTO_PATH");
        index = getIntent().getIntExtra("index", 0);

        browseViewPager = findViewById(R.id.browseViewPager);

        browseAdapter = new BrowseAdapter(this, photoPathList);
        browseViewPager.setAdapter(browseAdapter);

        browseViewPager.setCurrentItem(index);
    }

    private void initListener() {
        browseViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @TargetApi(21)
    private void initSharedElement() {
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                String url = photoPathList.get(browseViewPager.getCurrentItem());
                sharedElements.clear();
                sharedElements.put(url, browseAdapter.getPrimaryItem());
            }
        });
    }

    @Override
    public void supportFinishAfterTransition() {
        Intent data = new Intent();
        data.putExtra("index", browseViewPager.getCurrentItem());
        setResult(RESULT_OK, data);
        super.supportFinishAfterTransition();
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("index", browseViewPager.getCurrentItem());
        setResult(RESULT_OK, data);
        super.supportFinishAfterTransition();
    }
}
