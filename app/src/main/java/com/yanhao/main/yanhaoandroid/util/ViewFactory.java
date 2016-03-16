package com.yanhao.main.yanhaoandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yanhao.main.yanhaoandroid.R;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param text
	 * @return
	 */
	private static Context ctx;
	public static ImageView getImageView(Context context, String url) {
		ctx = context;
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		//ImageLoader.getInstance().displayImage(url, imageView);
		Glide.with(ctx).load(url).into(imageView);
		return imageView;
	}
}
