package com.example.shoppingcart.bean;

import com.example.shoppingcart.R;

import java.util.ArrayList;

public class GoodsInfo {
    public long rowid; // 行号
    public int sn; // 序号
    public String name; // 名称
    public String desc; // 描述
    public float price; // 价格
    public String thumb_path; // 小图的保存路径
    public String pic_path; // 大图的保存路径
    public int thumb; // 小图的资源编号
    public int pic; // 大图的资源编号

    public GoodsInfo() {
        rowid = 0L;
        sn = 0;
        name = "";
        desc = "";
        price = 0;
        thumb_path = "";
        pic_path = "";
        thumb = 0;
        pic = 0;
    }

    // 声明一个手机商品的名称数组
    private static String[] mNameArray = {
            "美国小羊", "三眼仔", "抱抱龙", "Bunnies", "复读鸭", "玩偶蹲仙人掌", "小棕熊","复古贴画"
    };
    // 声明一个手机商品的描述数组
    private static String[] mDescArray = {
            "美国小羊公仔毛绒玩具正版小坐羊bunnies by the bay网红玩偶绵羊",
            "玩具总动员圣诞版三眼仔手办玩偶场景汉堡包薯条可乐摆件公仔玩具",
            "罕见玩具总动员抱抱龙绿恐龙手办公仔玩具摆件",
            "美国Bunnies By The Bay正版小羊公仔衣服毛绒玩具生日礼物送女友",
            "沙雕加油复读鸭正版网红鸭子机录音学人会说话的鸭鸭公仔学舌玩具",
            "BIBIG的杂货铺可爱韩国ins少女心玩偶蹲仙人掌表情包治愈公仔娃娃",
            "复古看书小棕熊适用于苹果12 Pro Max/X/华为OPPO小米透明手机壳",
            "合作系列-复古贴画适用于苹果11/Xs/华为OPPO小米vivo透明手机壳"
    };
    // 声明一个手机商品的价格数组
    private static float[] mPriceArray = {188, 399, 290, 275, 198, 598,134,154};
    // 声明一个手机商品的小图数组
    private static int[] mThumbArray = {
            R.drawable.xiaoyang_s, R.drawable.sanyan_s, R.drawable.konglong_s,
            R.drawable.yangzai_s, R.drawable.yazi_s, R.drawable.chouzi_s,
            R.drawable.one_s,R.drawable.kefour_s
    };
    // 声明一个手机商品的大图数组
    private static int[] mPicArray = {
            R.drawable.xiaoyang, R.drawable.sanyan, R.drawable.konglong,
            R.drawable.yangzai, R.drawable.yazi, R.drawable.chouzi,
            R.drawable.one,R.drawable.kefour
    };

    // 获取默认的手机信息列表
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.name = mNameArray[i];
            info.desc = mDescArray[i];
            info.price = mPriceArray[i];
            info.thumb = mThumbArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }
}