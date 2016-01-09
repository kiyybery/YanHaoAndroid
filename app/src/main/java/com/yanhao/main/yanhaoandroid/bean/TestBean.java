package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/11/20 0020.
 */
public class TestBean {

    public int id;
    public String img;
    public String test_title;
    public String peopleNum;
    public int test_tag;
    public String webUrl;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getTest_title() {
        return test_title;
    }

    public void setTest_title(String test_title) {
        this.test_title = test_title;
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
                "id=" + id +
                ", img='" + img + '\'' +
                ", test_title='" + test_title + '\'' +
                ", peopleNum='" + peopleNum + '\'' +
                ", test_tag=" + test_tag +
                '}';
    }
}
