package com.yanhao.main.yanhaoandroid.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/12 0012.
 */
public class BannerBean {

    private int bannerType;
    private String bannerParam;
    private String bannerUrl;
    private String actionUrl;
    private String[] urlList;

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public String getBannerParam() {
        return bannerParam;
    }

    public void setBannerParam(String bannerParam) {
        this.bannerParam = bannerParam;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String[] getUrlList() {
        return urlList;
    }

    public void setUrlList(String[] urlList) {
        this.urlList = urlList;
    }
}
