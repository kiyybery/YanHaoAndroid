package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class CollectionBean {

    private int imageView;
    private String title;
    private String date;

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
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
