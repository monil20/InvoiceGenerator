package com.example.monilandharia.invoice;

import android.widget.ImageView;

/**
 * Created by Monil Andharia on 30-Mar-16.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int flag=0;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
