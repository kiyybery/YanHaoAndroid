package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/11/11 0011.
 */
public class PersonalOrderBean {

    public String image;
    public String presonalName;
    public String type;
    public String time;
    public String issue;
    public String mobile;
    public String note;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
