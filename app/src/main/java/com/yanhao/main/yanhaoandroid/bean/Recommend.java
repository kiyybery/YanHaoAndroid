package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class Recommend {

    public int userId;
    public String pic;
    public String subName;
    public int level_pic;
    public String distence;
    public String describe;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getLevel_pic() {
        return level_pic;
    }

    public void setLevel_pic(int level_pic) {
        this.level_pic = level_pic;
    }

    public String getDistence() {
        return distence;
    }

    public void setDistence(String distence) {
        this.distence = distence;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
