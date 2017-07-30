package com.qql.dagger.recommend.fragment;

import com.green.dao.output.Book;
import com.qql.dagger.recommend.Constants;
import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.adapter.BookSelfAdapter;
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
    @BindView(R.id.gridview)
    DraggableGridView draggableGridView;
    private List<Book> books;
    private BookSelfAdapter adapter;
    @Override
    public void showError(String msg) {

    }

    @Override
    public void showBookSelfList(List<Book> bbs) {
        books = bbs;
        adapter = new BookSelfAdapter(getActivity(),bbs);
        draggableGridView.setAdapter(adapter);
        setListeners();
    }

    private void setListeners(){
        draggableGridView.setOnRearrangeListener(new OnRearrangeListener() {
            public void onRearrange(int oldIndex, int newIndex) {
                try {
                    Book word = books.remove(oldIndex);
                    books.add(newIndex, word);
                }catch (Exception e){
                    LogUtil.printException(e);
                }
            }
        });
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bookself;
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
