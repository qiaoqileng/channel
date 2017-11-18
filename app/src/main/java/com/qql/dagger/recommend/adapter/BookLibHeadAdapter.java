package com.qql.dagger.recommend.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by T-46 on 2017/8/2.
 */

public abstract class BookLibHeadAdapter extends RecyclerView.Adapter<BookLibHeadAdapter.MyViewHolder>
        implements DraggableItemAdapter<BookLibHeadAdapter.MyViewHolder>, View.OnClickListener {
    private Context context;
    private List<String> paths = new ArrayList<>();
    private final String basePath = Environment.getExternalStorageDirectory().toString();
    public BookLibHeadAdapter() {
        paths.add(basePath);
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.item_book_lib_category, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String path = paths.get(position);
        if (!TextUtils.isEmpty(path)){
            holder.text.setText(getShortPath(path));
            holder.text.setTag(position);
            holder.text.setTag(position);
            holder.text.setOnClickListener(this);
        }
    }

    private String getShortPath(String path){
        try{
            int index = path.lastIndexOf("/");
            return path.substring(index+1)+ " >";
        }catch (Exception e){
            LogUtil.printException(e);
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return paths ==null?0: paths.size();
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
    public void onMoveItem(int fromPosition, int toPosition) {

    }

    @Override
    public long getItemId(int position) {
        return paths.get(position).hashCode();
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        LogUtil.d("onCheckCanDrop: " + " draggingPosition= " +  draggingPosition + " dropPosition=" + dropPosition);
        return true;
    }

    public void addPath(String path){
        paths.add(path);
        notifyDataSetChanged();
    }

    public boolean removeLast(){
        if (paths.size()<=1){
            // TODO: 2017/10/26 back
            return false;
        } else {
            paths.remove(paths.size()-1);
            onItemClick(paths.get(paths.size()-1));
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        try{
            int position = (int) v.getTag();
            if (position < paths.size()-1){
                String path = paths.get(position);
                onItemClick(path);
                for (int i=position+1;i<paths.size();i++){
                    paths.remove(i);
                }
                notifyDataSetChanged();
            }
        }catch (Exception e){
            LogUtil.printException(e);
        }
    }

    protected abstract void onItemClick(String path);

    static class MyViewHolder extends AbstractDraggableItemViewHolder {
        @BindView(R.id.text)
        TextView text;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,itemView);
        }
    }
}
