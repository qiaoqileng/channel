package com.qql.dagger.recommend.model.bean;

import java.io.File;

public class MyFile {

    private File myFile;
    private boolean selected;

    public MyFile(File file) {
        myFile = file;
    }

    public File getMyFile() {
        return myFile;
    }

    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isDirectory() {
        return myFile.isDirectory();
    }

    public boolean exists() {
        return myFile.exists();
    }

    public long size() {
        return myFile.length();
    }

    public long lastModified() {
        return myFile.lastModified();
    }
}
