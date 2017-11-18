package com.qql.dagger.recommend.utils;

import android.text.TextUtils;

import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.model.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qql on 2017/10/13.
 */

public class TypeUtils {
    private static List<TypeBean> types = getTypes();

    public static boolean acceptType(String path){
        if (TextUtils.isEmpty(path)){
            return false;
        } else {
            for (TypeBean type:types){
                if (path.contains(type.getType())){
                    return true;
                }
            }
            return false;
        }
    }

    public static int getTypeResId(String path) {
        if (TextUtils.isEmpty(path)){
            return 0;
        } else {
            for (TypeBean type:types){
                if (path.contains(type.getType())){
                    return type.getResId();
                }
            }
            return 0;
        }
    }

    private static List<TypeBean> getTypes(){
        List<TypeBean> list = new ArrayList<>();
        list.add(new TypeBean(".epub", R.mipmap.ic_epub, "EPUB类型"));
        list.add(new TypeBean(".oeb", R.mipmap.oeb, "OEB类型"));
        list.add(new TypeBean(".fb2", R.mipmap.fb2, "FB2类型"));
        list.add(new TypeBean(".mobi", R.mipmap.mobi, "MOBI类型"));
        list.add(new TypeBean(".prc", R.mipmap.prc, "PRC类型"));
        list.add(new TypeBean(".txt", R.mipmap.txt, "TXT类型"));
        list.add(new TypeBean(".rtf", R.mipmap.rtf, "RTF类型"));
        list.add(new TypeBean(".pdf", R.mipmap.pdf, "PDF类型"));
        list.add(new TypeBean(".djvu", R.mipmap.djvu, "DJVU类型"));
        list.add(new TypeBean(".cbr", R.mipmap.cbr, "CBR类型"));
        list.add(new TypeBean(".cbz", R.mipmap.cbz, "CBZ类型"));
        return list;
    }

}

