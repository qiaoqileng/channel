package com.qql.dagger.recommend.model.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qql.dagger.recommend.model.bean.BannerBean;

/**
 * Created by qiao on 2016/12/23.
 */

public class NetImageHolder implements Holder<BannerBean>{
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerBean data) {
        Glide.with(context).load(data.getImageUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(
                new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }
}
