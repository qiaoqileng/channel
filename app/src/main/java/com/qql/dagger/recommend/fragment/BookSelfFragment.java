package com.qql.dagger.recommend.fragment;

import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.green.dao.output.Book;
import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import com.qql.dagger.recommend.Constants;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.adapter.BookSelfAdapter;
import com.qql.dagger.recommend.adapter.BookSelfsAdapter;
import com.qql.dagger.recommend.base.BaseFragment;
import com.qql.dagger.recommend.presenter.BookSelfPresenter;
import com.qql.dagger.recommend.presenter.contract.BookSelfContract;
import com.qql.dagger.recommend.utils.LogUtil;
import com.qql.dagger.recommend.view.DraggableGridView;
import com.qql.dagger.recommend.view.MainGroupView;
import com.qql.dagger.recommend.view.OnRearrangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by qql on 2017/7/29.
 */

public class BookSelfFragment extends BaseFragment<BookSelfPresenter> implements BookSelfContract.View{
//    @BindView(R.id.gridview)
//    DraggableGridView draggableGridView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<Book> books;
    private GridLayoutManager mLayoutManager;
    private RecyclerViewDragDropManager mRecyclerViewDragDropManager;
    private BookSelfsAdapter adapter;
    private RecyclerView.Adapter mWrappedAdapter;

    //    private BookSelfAdapter adapter;
    @Override
    public void showError(String msg) {

    }

    @Override
    public void showBookSelfList(List<Book> bbs) {
        books = bbs;
        initView();
//        adapter = new BookSelfAdapter(getActivity(),bbs);
//        draggableGridView.setAdapter(adapter);
//        setListeners();
    }

    private void initView() {
        mLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);

        // drag & drop manager
        mRecyclerViewDragDropManager = new RecyclerViewDragDropManager();
        mRecyclerViewDragDropManager.setDraggingItemShadowDrawable(
                (NinePatchDrawable) ContextCompat.getDrawable(getContext(), R.drawable.material_shadow_z3));
        // Start dragging after long press
        mRecyclerViewDragDropManager.setInitiateOnLongPress(true);
        mRecyclerViewDragDropManager.setInitiateOnMove(false);
        mRecyclerViewDragDropManager.setLongPressTimeout(750);

        // setup dragging item effects (NOTE: DraggableItemAnimator is required)
        mRecyclerViewDragDropManager.setDragStartItemAnimationDuration(250);
        mRecyclerViewDragDropManager.setDraggingItemAlpha(0.8f);
        mRecyclerViewDragDropManager.setDraggingItemScale(1.3f);
        mRecyclerViewDragDropManager.setDraggingItemRotation(15.0f);

        //adapter
        adapter = new BookSelfsAdapter(books);

        mWrappedAdapter = mRecyclerViewDragDropManager.createWrappedAdapter(adapter);      // wrap for dragging

        GeneralItemAnimator animator = new DraggableItemAnimator(); // DraggableItemAnimator is required to make item animations properly.

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        mRecyclerView.setItemAnimator(animator);

        // additional decorations
        //noinspection StatementWithEmptyBody
        if (supportsViewElevation()) {
            // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
        } else {
            mRecyclerView.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) ContextCompat.getDrawable(getContext(), R.drawable.material_shadow_z1)));
        }

        mRecyclerViewDragDropManager.attachRecyclerView(mRecyclerView);
    }

    @Override
    public void onPause() {
        mRecyclerViewDragDropManager.cancelDrag();
        super.onPause();
    }

    private boolean supportsViewElevation() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

    @Override
    public void onDestroyView() {
        if (mRecyclerViewDragDropManager != null) {
            mRecyclerViewDragDropManager.release();
            mRecyclerViewDragDropManager = null;
        }

        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }

        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
            mWrappedAdapter = null;
        }
        adapter = null;
        mLayoutManager = null;

        super.onDestroyView();
    }

//    private void setListeners(){
//        draggableGridView.setOnRearrangeListener(new OnRearrangeListener() {
//            public void onRearrange(int oldIndex, int newIndex) {
//                try {
//                    Book word = books.remove(oldIndex);
//                    books.add(newIndex, word);
//                }catch (Exception e){
//                    LogUtil.printException(e);
//                }
//            }
//        });
//    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bookself_new;
    }

    @Override
    protected void initEventAndData() {
//        testBooks();
        Map<String,String> param = new HashMap<>();
        param.put(Constants.USER_ID,"qql");
        mPresenter.findBookSelfList(param);
    }

    private void testBooks(){
        for(int i=0;i<10;i++){
            Book book = new Book();
            book.setUser_id("qql");
            book.setAuthor("鲁迅");
            book.setCover_url("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2790062669,1046332026&fm=117&gp=0.jpg");
            book.setRead_rate(i*10d);
            book.setTitle("狂人日记" + i);
            mPresenter.insertBook(book);
        }
    }

}
