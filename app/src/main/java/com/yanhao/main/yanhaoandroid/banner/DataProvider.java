package com.yanhao.main.yanhaoandroid.banner;

import java.util.ArrayList;

public class DataProvider {
    public static String[] titles = new String[]{
            "伪装者:胡歌演绎'痞子特工'",
            "无心法师:生死离别!月牙遭虐杀",
            "花千骨:尊上沦为花千骨",
            "综艺饭:胖轩偷看夏天洗澡掀波澜",
            "碟中谍4:阿汤哥高塔命悬一线,超越不可能",
    };
    public static String[] urls = new String[]{//640*360 360/640=0.5625
            /*"http://photocdn.sohu.com/tvmobilemvms/20150907/144160323071011277.jpg",//伪装者:胡歌演绎"痞子特工"
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160286644953923.jpg",//花千骨:尊上沦为花千骨
            "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
            "http://7xop51.com1.z0.glb.clouddn.com/banner4.jpg"*/
            "http://7xop51.com1.z0.glb.clouddn.com/yanhao_banner_1.jpg",
            "http://7xop51.com1.z0.glb.clouddn.com/yanhao_banner_2.jpg",
            "http://7xop51.com1.z0.glb.clouddn.com/yanhao_banner_3.jpg"
    };

    public static String[] constantUrls = new String[]{

            "http://7xop51.com1.z0.glb.clouddn.com/zhuanjia_lilu.jpg",
            "http://7xop51.com1.z0.glb.clouddn.com/zhuanjia_songyumei.jpg"
    };

    public static ArrayList<BannerItem> getList() {
        ArrayList<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = urls[i];
            //item.title = titles[i];

            list.add(item);
        }

        return list;
    }

    public static ArrayList<BannerItem> getConstantList() {
        ArrayList<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < constantUrls.length; i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = constantUrls[i];
            //item.title = titles[i];

            list.add(item);
        }

        return list;
    }

    /*public static ArrayList<Integer> geUsertGuides() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.mipmap.guide_img_1);
        list.add(R.mipmap.guide_img_2);
        list.add(R.mipmap.guide_img_3);
        list.add(R.mipmap.guide_img_4);

        return list;
    }*/

    /*public static Class<? extends ViewPager.PageTransformer> transformers[] = new Class[]{
            DepthTransformer.class,
            FadeSlideTransformer.class,
            FlowTransformer.class,
            RotateDownTransformer.class,
            RotateUpTransformer.class,
            ZoomOutSlideTransformer.class,
    };*/
}
