package com.j7arsen.imageloader.main;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.j7arsen.imageloader.R;

import java.util.ArrayList;

/**
 * Created by arsen on 21.03.17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<String> mImageList;

    public ImageAdapter(ArrayList<String> data) {
        mImageList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = mImageList.get(position);
        initView(holder, data);
    }

    @Override
    public int getItemCount() {
        return mImageList == null ? 0 : mImageList.size();
    }

    private void initView(ViewHolder holder, String data) {
        String url = data;
        if (url != null) {
            Uri uri = Uri.parse(url);
            holder.sdvImage.setImageURI(uri);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdvImage;


        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View view) {
            sdvImage = (SimpleDraweeView) view.findViewById(R.id.sdv_image);


        }
    }


}
