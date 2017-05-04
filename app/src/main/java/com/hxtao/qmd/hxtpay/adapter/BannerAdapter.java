package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;
import com.youth.banner.loader.ImageLoader;

/**
 * @Description:
 * @Author: Cyf on 2017/3/13.
 */

public class BannerAdapter extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageLoadUtil.disPlayImage((String) path,imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        return super.createImageView(context);
    }
}
