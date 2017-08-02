package com.qql.dagger.recommend.utils;

import com.green.dao.output.Book;

import java.util.List;

/**
 * Created by T-46 on 2017/8/2.
 */

public class CommonUtils {

    public static void move(List<Book> books,int fromPosition, int toPosition){
        if (fromPosition == toPosition || books == null || books.size() < 1) {
            return;
        }

        final Book item = books.remove(fromPosition);

        books.add(toPosition, item);
    }
}
