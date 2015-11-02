package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConstantBean {

    public int imageview;
    public String constantName;
    public String level;
    public String area;

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

    @Override
    public String toString() {
        return "ConstantBean{" +
                "imageview=" + imageview +
                ", constantName='" + constantName + '\'' +
                ", level='" + level + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
