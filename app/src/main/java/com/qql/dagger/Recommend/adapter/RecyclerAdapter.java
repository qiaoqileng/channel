package com.qql.dagger.recommend.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qql.dagger.recommend.App;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.model.bean.GankItemBean;
import com.qql.dagger.recommend.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiao on 2016/11/25.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<GankItemBean> GankItemBeans;
    private OnItemClickListener onItemClickListener;

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_girl, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GankItemBean gankItemBean = GankItemBeans.get(position);

            if (gankItemBean.getHeight() > 0) {
                ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
                layoutParams.height = gankItemBean.getHeight();
            }
            holder.textView.setText(gankItemBean.getWho());
            Glide.with(context).load(gankItemBean.getUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(App.SCREEN_WIDTH / 2, App.SCREEN_WIDTH / 2) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if(holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                                if (gankItemBean.getHeight() <= 0) {
                                    int width = resource.getWidth();
                                    int height = resource.getHeight();
                                    int realHeight = (App.SCREEN_WIDTH / 2) * height / width;
                                    gankItemBean.setHeight(realHeight);
                                    ViewGroup.LayoutParams lp = holder.imageView.getLayoutParams();
                                    lp.height = realHeight;
                                }
                                holder.imageView.setImageBitmap(resource);
                            }
                        }
                    });
            holder.imageView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClickListener(position,view);
                }
            });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.who)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public int getItemCount() {
        return GankItemBeans == null ? 0 : GankItemBeans.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, View shareView);
    }
}
