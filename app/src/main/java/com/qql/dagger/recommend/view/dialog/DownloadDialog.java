package com.qql.dagger.recommend.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;

import com.qql.dagger.recommend.R;

/**
 * 下载dialog
 */
public class DownloadDialog {

    private Context context;

    public DownloadDialog(Context context) {
        this.context = context;
    }

    private void init(){
        LayoutInflater.from(context).inflate(R.layout.dialog_download,null);
    }
}
