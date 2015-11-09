package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class OrderBean {

    public int imageview;
    public String constantName;
    public String level;
    public String area;
    public String data;

    public int getImageview() {
        return imageview;
    }

    public void setImageview(int imageview) {
        this.imageview = imageview;
    }

    public String getConstantName() {
        return constantName;
    }

    public void setConstantName(String constantName) {
        this.constantName = constantName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "imageview=" + imageview +
                ", constantName='" + constantName + '\'' +
                ", level='" + level + '\'' +
                ", area='" + area + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
