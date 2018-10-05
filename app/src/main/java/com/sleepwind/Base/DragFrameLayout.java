package com.sleepwind.Base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class DragFrameLayout extends FrameLayout {

    public static final String TAG = "DragFramlayout";
    private ViewDragHelper viewDragHelper;
    private boolean mDragScale = true;                  // 拖动是否进行缩放变化
    private float sensitivity = 1.0f;


    public DragFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDrag();
    }

    private void initDrag() {

        viewDragHelper = ViewDragHelper.create(this, sensitivity, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                if (mNeedRelease) {
                    if (getContext() instanceof Activity) {
                        ((Activity) getContext()).onBackPressed();
                    }
                } else {
                    needDrag = false;
                    //让视图归位
                    viewDragHelper.settleCapturedViewAt(finalLeft, finalTop);
                    if(mDragScale){
                        releasedChild.setScaleX(1.0f);
                        releasedChild.setScaleY(1.0f);
                    }
                    invalidate();
                }
            }

            boolean mNeedRelease;

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);

                mNeedRelease = top > getHeight() * 0.25;//Release

                //ChangeBg
                float present = 1 - (top * 1.0f) / (getHeight());
                if (getContext() instanceof Activity) {
                    int alpah = Math.min((int) (255 * present), 255);
                    ((Activity) getContext()).getWindow().getDecorView().setBackgroundColor(Color.argb(alpah, 255, 255, 255));
                }
                //ChangeScale
                if(mDragScale){
                    float maxScale = Math.min(present, 1.0f);//Max,1.0f
                    float minScale = Math.max(0.5f, maxScale);//Min,5.0f;

                    changedView.setScaleX(minScale);
                    changedView.setScaleY(minScale);
                }
            }

            boolean needDrag;

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (needDrag) {
                    return top;
                }
                if (top < 0) {
                    top = 0;
                } else if (top > 100) {//释放允许任何方向拖拽
                    needDrag = true;
                }
                return top;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return needDrag ? left : 0;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getHeight() / 2;
            }
        });
    }

    int finalLeft;
    int finalTop;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        finalLeft = getChildAt(0).getLeft();
        finalTop = getChildAt(0).getTop();
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
