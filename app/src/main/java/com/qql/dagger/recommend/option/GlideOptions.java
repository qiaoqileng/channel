package com.qql.dagger.recommend.option;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.qql.dagger.recommend.R;

/**
 * Created by qql on 2018/3/13.
 */

public class GlideOptions {

    public static RequestOptions defaultOption(){
        return new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }
}
