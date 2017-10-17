package com.qql.dagger.recommend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.green.dao.output.MyBook;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.utils.CommonUtils;
import com.qql.dagger.recommend.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by T-46 on 2017/8/2.
 */

public class BookSelfsAdapter extends RecyclerView.Adapter<BookSelfsAdapter.MyViewHolder>
        implements DraggableItemAdapter<BookSelfsAdapter.MyViewHolder>, View.OnClickListener {
    private List<MyBook> myBooks;
    private Context context;
    public BookSelfsAdapter(List<MyBook> myBooks) {
        this.myBooks = myBooks;
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.item_bookself, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyBook myBook = myBooks.get(position);
        if (myBook != null) {
            holder.bookTitle.setText(myBook.getTitle());
            holder.title.setText(myBook.getTitle());
//            holder.format.setText(myBook.getFormats());
            holder.readRate.setText("已读"+ myBook.getRead_rate() + "%");
            holder.cover.setTag(position);
            holder.cover.setOnClickListener(this);
            Glide.with(context).load(myBook.getCover_url()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.cover);
        }
    }

    @Override
    public int getItemCount() {
        return myBooks ==null?0: myBooks.size();
    }

    @Override
    public boolean onCheckCanStartDrag(MyViewHolder holder, int position, int x, int y) {
        LogUtil.d("onCheckCanStartDrag: holder=" + holder.toString() + " position= " +  position + " x=" + x + " y=" + y);
        return true;
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(MyViewHolder holder, int position) {
        LogUtil.d("onGetItemDraggableRange: holder=" + holder.toString() + " position= " +  position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return myBooks.get(position).getId();
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        LogUtil.d("onMoveItem: " + " fromPosition= " +  fromPosition + " toPosition=" + toPosition);
        if (fromPosition == toPosition) {
            return;
        }
        CommonUtils.move(myBooks,fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        LogUtil.d("onCheckCanDrop: " + " draggingPosition= " +  draggingPosition + " dropPosition=" + dropPosition);
        return true;
    }

    @Override
    public void onClick(View v) {
        try{
            int position = (int) v.getTag();
            MyBook myBook = myBooks.get(position);

        }catch (Exception e){
            LogUtil.printException(e);
        }
    }

    static class MyViewHolder extends AbstractDraggableItemViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.book_title)
        TextView bookTitle;
        @BindView(R.id.read_rate)
        TextView readRate;
        @BindView(R.id.format)
        TextView format;
        @BindView(R.id.cover)
        ImageView cover;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,itemView);
        }
    }
}
