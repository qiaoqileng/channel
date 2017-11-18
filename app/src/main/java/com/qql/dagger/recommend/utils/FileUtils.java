package com.qql.dagger.recommend.utils;

import com.green.dao.output.MyBook;
import com.qql.dagger.recommend.model.bean.MyFile;

import org.geometerplus.zlibrary.core.filesystem.ZLPhysicalFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qql on 2017/11/7.
 */

public class FileUtils {

    public static List<ZLPhysicalFile> transformFile(List<File> files){
        List<ZLPhysicalFile> myFiles = new ArrayList<>();
        if (files !=null&&files.size()>0){
            for (File file:files){
                myFiles.add(new ZLPhysicalFile(file));
            }
        }
        return myFiles;
    }

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
