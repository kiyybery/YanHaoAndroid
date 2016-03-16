package com.yanhao.main.yanhaoandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BannerList implements Parcelable{
	    private String image;
	    public String getImage() {
	        return image;
	    }
	    public void setImage(String image) {
	        this.image = image;
	    }
	    
	    
	    
	    public BannerList() {
	    }

	    public BannerList(Parcel in) {
	        image = in.readString();
	    }

	    public BannerList(String user_nname, String image,String user_username,String user_sign) {
	        this.image = image;
	    }

	    @Override
	    public int describeContents() {
	        // TODO Auto-generated method stub
	        return 0;
	    }

	    @SuppressWarnings("rawtypes")
	    public static final Creator CREATOR = new Creator() {
	        public BannerList createFromParcel(Parcel in) {
	            return new BannerList(in);
	        }

	        public BannerList[] newArray(int size) {
	            return new BannerList[size];
	        }
	    };

	    @Override
	    public void writeToParcel(Parcel dest, int flags) {
	        dest.writeString(image);
	        
	    }
}
