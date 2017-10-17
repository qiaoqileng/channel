package com.qql.dagger.recommend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.green.dao.output.MyBook;
import com.qql.dagger.recommend.R;

import java.util.List;

/**
 * Created by qql on 2017/7/29.
 */

public class BookSelfAdapter {
    private Context context;
    private List<MyBook> myBooks;

    public BookSelfAdapter(Context context, List<MyBook> myBooks) {
        this.context = context;
        this.myBooks = myBooks;
    }

    public int getCount() {
        return myBooks ==null?0: myBooks.size();
    }

    public MyBook getItem(int i) {
        return myBooks ==null?null: myBooks.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
//        if (view == null||view.getTag()==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_bookself,viewGroup);
            holder = new Holder();
            holder.bookTitle = (TextView) view.findViewById(R.id.book_title);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.readRate = (TextView) view.findViewById(R.id.read_rate);
            holder.format = (TextView) view.findViewById(R.id.format);
            holder.cover = (ImageView) view.findViewById(R.id.cover);
            view.setTag(holder);
//        } else {
//            holder = (Holder) view.getTag();
//        }
        MyBook myBook = getItem(i);
        if (myBook != null) {
            holder.bookTitle.setText(myBook.getTitle());
            holder.title.setText(myBook.getTitle());
//            holder.format.setText(myBook.getFormats());
            holder.readRate.setText("已读"+ myBook.getRead_rate() + "%");
            Glide.with(context).load(myBook.getCover_url()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.cover);
        }
        return view;
    }

    class Holder {
        TextView title,bookTitle,readRate,format;
        ImageView cover;
    }
}
