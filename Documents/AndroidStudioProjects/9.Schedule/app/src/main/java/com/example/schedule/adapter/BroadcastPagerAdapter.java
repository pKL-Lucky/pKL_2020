package com.example.schedule.adapter;

import java.util.ArrayList;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.schedule.bean.GoodsInfo;
import com.example.schedule.fragment.BroadcastFragment;

public class BroadcastPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<GoodsInfo> mGoodsList = new ArrayList<GoodsInfo>(); // 声明一个商品队列

    // 碎片页适配器的构造函数，传入碎片管理器与商品队列
    public BroadcastPagerAdapter(FragmentManager fm, ArrayList<GoodsInfo> goodsList) {
        super(fm);
        mGoodsList = goodsList;
    }

    // 获取碎片Fragment的个数
    public int getCount() {
        return mGoodsList.size();
    }

    // 获取指定位置的碎片Fragment
    public Fragment getItem(int position) {
        return BroadcastFragment.newInstance(position,
                mGoodsList.get(position).pic, mGoodsList.get(position).desc);
    }

    // 获得指定碎片页的标题文本
    public CharSequence getPageTitle(int position) {
        return mGoodsList.get(position).name;
    }

}

