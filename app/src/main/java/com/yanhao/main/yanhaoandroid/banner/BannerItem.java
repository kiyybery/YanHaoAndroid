package com.yanhao.main.yanhaoandroid.banner;

public class BannerItem {
    public String imgUrl;
    public String title;

    private int bannerType;
    private String bannerParam;
    private String bannerUrl;
    private String actionUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
}
