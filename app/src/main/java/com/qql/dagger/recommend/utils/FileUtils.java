package com.qql.dagger.recommend.utils;

import com.green.dao.output.MyBook;
import com.qql.dagger.recommend.model.bean.MyFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qql on 2017/11/7.
 */

public class FileUtils {

    public static List<MyBook> getBooksByFiles(List<MyFile> myFiles){
        List<MyBook> myBooks = new ArrayList<>();
        if (myFiles!=null&&myFiles.size()>0){
            for (MyFile file:myFiles){
                if (file.exists()&&file.isSelected()&&!file.isDirectory()){
                    MyBook book = getBookByFile(file);
                    myBooks.add(book);
                }
            }
        }
        return myBooks;
    }

    private static MyBook getBookByFile(MyFile file) {
        return null;
    }
}
