package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class CollectionBean {

    public int id;
    public String imageView;
    public String title;
    public String date;
    public String webUrl;

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CollectionBean{" +
                "imageView=" + imageView +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
