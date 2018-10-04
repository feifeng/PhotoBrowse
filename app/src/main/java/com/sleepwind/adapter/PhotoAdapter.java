package com.sleepwind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sleepwind.Base.BaseApplication;
import com.sleepwind.Base.URLS;
import com.sleepwind.R;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context context;
    private List<String> photoPathList;
    private ImageLoader imageLoader;

    public PhotoAdapter(Context context, List<String> photoPathList) {
        this.context = context;
        this.photoPathList = photoPathList;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_item, viewGroup, false);
        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String photo = photoPathList.get(position);

        int widht = (BaseApplication.getScreenWidth()) / 3;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widht, widht);
        holder.photoImage.setLayoutParams(params);
        imageLoader.displayImage(String.format(URLS.PHOTO, photo), holder.photoImage);
    }

    @Override
    public int getItemCount() {
        return photoPathList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView photoImage;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            photoImage = view.findViewById(R.id.photoImageView);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
