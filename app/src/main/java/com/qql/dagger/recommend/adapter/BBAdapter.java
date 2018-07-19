package com.qql.dagger.recommend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.model.bean.BBBean;
import com.qql.dagger.recommend.model.bean.Page;
import com.qql.dagger.recommend.model.bean.Product;
import com.qql.dagger.recommend.option.GlideOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qql on 2017/4/8.
 */

public class BBAdapter extends BaseAdapter {
    private Context context;
    private Page<Product> datas;

    public BBAdapter(Context context, Page<Product> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas==null?0:datas.getPageSize();
    }

    @Override
    public Product getItem(int i) {
        return datas.getList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_bb,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Product bean = getItem(i);
        Glide.with(context).asBitmap().load(bean.getHead_img()).apply(GlideOptions.defaultOption()).into(holder.bbImag);
        holder.bbName.setText(bean.getName());
        holder.price.setText(String.format("¥ %s", bean.getPrice()));
        holder.shopName.setText(bean.getTb_shop_name());
        return view;
    }

    class ViewHolder{
        @BindView(R.id.bb_img)
        ImageView bbImag;
        @BindView(R.id.bb_name)
        TextView bbName;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.price)
        TextView price;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
