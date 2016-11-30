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
import com.qql.dagger.recommend.bean.Student;
import com.qql.dagger.recommend.databinding.ItemStudentBinding;
import com.qql.dagger.recommend.utils.LogUtil;

import java.util.List;

import io.realm.Realm;

/**
 * Created by qiao on 2016/11/25.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private List<Student> students;

    public RecyclerAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
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
        return Math.round((float) App.SCREEN_WIDTH / (float) students.get(position).getHeight() * 10f);
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_student, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        final Student student = students.get(position);

        if (holder.getBinding() instanceof ItemStudentBinding) {
//            Glide.with(context).load(student.getUrl())
//                    .asBitmap()
//                    .into(((ItemStudentBinding) holder.getBinding()).imageView);
            final ItemStudentBinding binding = (ItemStudentBinding) holder.getBinding();
            if (student.getHeight() > 0) {
                ViewGroup.LayoutParams layoutParams = binding.imageView.getLayoutParams();
                layoutParams.height = student.getHeight();
            }

            Glide.with(context).load(student.getUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(App.SCREEN_WIDTH / 2, App.SCREEN_WIDTH / 2) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if(holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                                if (student.getHeight() <= 0) {
                                    int width = resource.getWidth();
                                    int height = resource.getHeight();
                                    int realHeight = (App.SCREEN_WIDTH / 2) * height / width;
                                    Realm remlm = Realm.getDefaultInstance();
                                    remlm.beginTransaction();
                                    student.setHeight(realHeight);
                                    remlm.commitTransaction();
                                    ViewGroup.LayoutParams lp = binding.imageView.getLayoutParams();
                                    lp.height = realHeight;
                                }
                                binding.imageView.setImageBitmap(resource);
                            }
                        }
                    });
        }
        holder.getBinding().setVariable(BR.student, student);
        holder.getBinding().executePendingBindings();
        LogUtil.d(position+": "+student.toString());
    }

    @Override
    public int getItemCount() {
        return students == null ? 0 : students.size();
    }
}
