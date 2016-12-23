package com.qql.dagger.recommend.animotion;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by qiao on 2016/12/23.
 */

public class ZoomInTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {
        final float scale = position < 0 ? position + 1f : Math.abs(1f - position);
        view.setScaleX(scale);
        view.setScaleY(scale);
        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(view.getHeight() * 0.5f);
        if (position < -1f || position > 1f) view.setAlpha(0f);
        else view.setAlpha(1f - (scale - 1f));
    }
}
