package com.sleepwind.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.sleepwind.Base.PhotoFragment;

import java.util.ArrayList;

public class BrowseAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private ArrayList<String> photoPathList = new ArrayList<String>();

    public BrowseAdapter(Context context, FragmentManager fragmentManager, ArrayList<String> photoPathList) {
        super(fragmentManager);
        this.context = context;
        this.photoPathList = photoPathList;
    }

    @Override
    public int getCount() {
        return photoPathList.size();
    }

    @Override
    public Fragment getItem(int i) {
        return PhotoFragment.newFragment(i, photoPathList);
    }
}
