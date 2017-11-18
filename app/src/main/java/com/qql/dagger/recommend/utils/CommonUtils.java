package com.qql.dagger.recommend.utils;

import com.green.dao.output.MyBook;

import java.math.BigDecimal;
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

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }

}
