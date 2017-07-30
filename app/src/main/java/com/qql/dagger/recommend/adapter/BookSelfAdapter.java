package com.qql.dagger.recommend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.green.dao.output.Book;
import com.qql.dagger.recommend.R;

import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * Created by qql on 2017/7/29.
 */

public class BookSelfAdapter {
    private Context context;
    private List<Book> books;

    public BookSelfAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    public int getCount() {
        return books==null?0:books.size();
    }

    public Book getItem(int i) {
        return books==null?null:books.get(i);
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
        Book book = getItem(i);
        if (book != null) {
            holder.bookTitle.setText(book.getTitle());
            holder.title.setText(book.getTitle());
//            holder.format.setText(book.getFormats());
            holder.readRate.setText("已读"+ book.getRead_rate() + "%");
            Glide.with(context).load(book.getCover_url()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.cover);
        }
        return view;
    }

    class Holder {
        TextView title,bookTitle,readRate,format;
        ImageView cover;
    }
}
