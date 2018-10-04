package com.sleepwind.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PhotoItemDecoration extends RecyclerView.ItemDecoration {

    public PhotoItemDecoration() {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int postion = parent.getChildAdapterPosition(view);
        if ((postion + 1) % 3 == 0) {
            outRect.right = 0;
        }else {
            outRect.left = 0;
            outRect.top = 0;
            outRect.right = 4;
            outRect.bottom = 4;
        }
    }
}
