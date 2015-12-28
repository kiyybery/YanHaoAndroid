package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConstantBean {

    public int userid;
    public String imageview;
    public String constantName;
    public int level;
    public String area;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getImageview() {
        return imageview;
    }

    public void setImageview(String imageview) {
        this.imageview = imageview;
    }

    public String getConstantName() {
        return constantName;
    }

    public void setConstantName(String constantName) {
        this.constantName = constantName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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
