package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/11/11 0011.
 */
public class PersonalOrderBean {

    public int image;
    public String presonalName;
    public String type;
    public String time;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getPresonalName() {
        return presonalName;
    }

    public void setPresonalName(String presonalName) {
        this.presonalName = presonalName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "PersonalOrderBean{" +
                "image=" + image +
                ", presonalName='" + presonalName + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
