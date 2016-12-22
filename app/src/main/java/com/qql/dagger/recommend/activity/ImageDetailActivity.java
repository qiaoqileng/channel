package com.qql.dagger.recommend.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.base.SimpleActivity;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by codeest on 16/8/20.
 */

public class ImageDetailActivity extends SimpleActivity {
    @BindView(R.id.iv_girl_detail)
    ImageView ivGirlDetail;

    Bitmap bitmap;
    PhotoViewAttacher mAttacher;

    String url;
    String id;

    @Override
    protected int getLayout() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void initEventAndData() {
        setToolBar("");
        Intent intent = getIntent();
        url = intent.getExtras().getString("url");
        id = intent.getExtras().getString("id");
        if (url != null) {
            Glide.with(mContext).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    bitmap = resource;
                    ivGirlDetail.setImageBitmap(resource);
                    mAttacher = new PhotoViewAttacher(ivGirlDetail);
                }
            });
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                finishAfterTransition();
            }else {
                finish();
            }
        }
    }
}
