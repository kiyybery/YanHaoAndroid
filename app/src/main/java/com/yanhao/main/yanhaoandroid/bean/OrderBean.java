package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class OrderBean {

    public String imageview;
    public String constantName;
    public String level;
    public String area;
    public String data;
    public String address;
    public String servicetel;
    public int reservationId;
    public int payStatus;

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServicetel() {
        return servicetel;
    }

    public void setServicetel(String servicetel) {
        this.servicetel = servicetel;
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
