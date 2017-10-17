package com.qql.dagger.recommend.utils;

import com.green.dao.output.MyBook;

import java.util.List;

/**
 * Created by T-46 on 2017/8/2.
 */

public class CommonUtils {

    public static void move(List<MyBook> myBooks, int fromPosition, int toPosition){
        if (fromPosition == toPosition || myBooks == null || myBooks.size() < 1) {
            return;
        }

        final MyBook item = myBooks.remove(fromPosition);

        myBooks.add(toPosition, item);
    }
}
