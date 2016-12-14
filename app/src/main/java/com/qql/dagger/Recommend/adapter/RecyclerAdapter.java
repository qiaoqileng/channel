package com.qql.dagger.recommend.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.BR;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.databinding.ItemGirlBinding;
import com.qql.dagger.recommend.model.bean.GankItemBean;
import com.qql.dagger.recommend.utils.LogUtil;

import java.util.List;

/**
 * Created by qiao on 2016/11/25.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private List<GankItemBean> GankItemBeans;

    public RecyclerAdapter(Context context, List<GankItemBean> GankItemBeans) {
        this.context = context;
        this.GankItemBeans = GankItemBeans;
    }

    /**
     * 在StaggeredGridLayoutManager瀑布流中,当需要依据图片实际相对高度,不断动态设置ImageView的LayoutParams时,
     * 会导致快速滑动状态下产生重新排列,重写getItemViewType并设置StaggeredGridLayoutManager.GAP_HANDLING_NONE解决了这个问题，原因目前未知
     * https://github.com/oxoooo/mr-mantou-android/blob/master/app/src/main/java/ooo/oxo/mr/MainAdapter.java
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return Math.round((float) App.SCREEN_WIDTH / (float) GankItemBeans.get(position).getHeight() * 10f);
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_girl, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        final GankItemBean GankItemBean = GankItemBeans.get(position);

        if (holder.getBinding() instanceof ItemGirlBinding) {
//            Glide.with(context).load(GankItemBean.getUrl())
//                    .asBitmap()
//                    .into(((ItemGankItemBeanBinding) holder.getBinding()).imageView);
            final ItemGirlBinding binding = (ItemGirlBinding) holder.getBinding();
            if (GankItemBean.getHeight() > 0) {
                ViewGroup.LayoutParams layoutParams = binding.imageView.getLayoutParams();
                layoutParams.height = GankItemBean.getHeight();
            }

            Glide.with(context).load(GankItemBean.getUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(App.SCREEN_WIDTH / 2, App.SCREEN_WIDTH / 2) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if(holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                                if (GankItemBean.getHeight() <= 0) {
                                    int width = resource.getWidth();
                                    int height = resource.getHeight();
                                    int realHeight = (App.SCREEN_WIDTH / 2) * height / width;
                                    GankItemBean.setHeight(realHeight);
                                    ViewGroup.LayoutParams lp = binding.imageView.getLayoutParams();
                                    lp.height = realHeight;
                                }
                                binding.imageView.setImageBitmap(resource);
                            }
                        }
                    });
        }
        holder.getBinding().setVariable(BR.girl, GankItemBean);
        holder.getBinding().executePendingBindings();
        LogUtil.d(position+": "+GankItemBean.toString());
    }

    @Override
    public int getItemCount() {
        return GankItemBeans == null ? 0 : GankItemBeans.size();
    }
}
