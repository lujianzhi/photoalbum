package com.lujianzhi.photoalbum.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lujianzhi.photoalbum.R;
import com.yancy.imageselector.ImageLoader;

/***
 * Created by Lawson on 2016/5/19.
 */
public class GlideLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.default_photo)
                .centerCrop()
                .into(imageView);
    }
}
