package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/11/20 0020.
 */
public class TestBean {

    public int img;
    public String test_title;
    public String time;
    public int test_tag;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTest_title() {
        return test_title;
    }

    public void setTest_title(String test_title) {
        this.test_title = test_title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTest_tag() {
        return test_tag;
    }

    public void setTest_tag(int test_tag) {
        this.test_tag = test_tag;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "img=" + img +
                ", test_title='" + test_title + '\'' +
                ", time='" + time + '\'' +
                ", test_tag=" + test_tag +
                '}';
    }
}
