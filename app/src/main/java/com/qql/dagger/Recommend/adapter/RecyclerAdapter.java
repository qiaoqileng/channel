package com.qql.dagger.recommend.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.qql.dagger.recommend.BR;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.bean.Student;

import java.util.List;

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

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_student, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        final Student student = students.get(position);
        holder.getBinding().setVariable(BR.student, student);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return students == null ? 0 : students.size();
    }
}
