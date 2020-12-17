package com.example.shopping.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.shopping.R;
import com.example.shopping.adapter.HomePageAdapter;
import com.example.shopping.bean.GoodsInfo;
import com.example.shopping.database.GoodsDBHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class HomePageFragment extends Fragment implements View.OnClickListener {
    private Banner banner;
    private RecyclerView recyclerView;
    private GoodsDBHelper mGoodsHelper;
    private HomePageAdapter adapter;
    private ArrayList<GoodsInfo> goodsArray;
    private PopupWindow popupWindow;
    private ImageView iv_more;
    private SmartRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && isAdded()) {
            //显示
            initData();
        }
    }

    private void initView(View view) {
        banner = view.findViewById(R.id.banner);
        iv_more = view.findViewById(R.id.iv_more);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new HomePageAdapter(R.layout.item_home_page);
        recyclerView.setAdapter(adapter);
        iv_more.setOnClickListener(this);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableNestedScroll(true);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    private void showPopupWindow() {
        //获取自定义菜单的布局文件
        final View popupWindow_view = getLayoutInflater().inflate(R.layout.wechat_menu_layout, null, false);
        //创建popupWindow，设置宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        //内部控件的点击事件
        LinearLayout line1 = popupWindow_view.findViewById(R.id.line1);
        line1.setOnClickListener(this);
        LinearLayout line2 = popupWindow_view.findViewById(R.id.line2);
        line2.setOnClickListener(this);
        LinearLayout line3 = popupWindow_view.findViewById(R.id.line3);
        line3.setOnClickListener(this);
        //设置菜单的显示位置
        popupWindow.showAsDropDown(iv_more, 0, 0);
        //兼容5.0点击其他位置隐藏popupWindow
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //必须写 v.performClick();解决与单击事件的冲突
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //如果菜单不为空，且菜单正在显示
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();//隐藏菜单
                            popupWindow = null;//初始化菜单
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        view.performClick();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    private void initData() {
        mGoodsHelper = GoodsDBHelper.getInstance(getActivity(), 1);
        // 打开商品数据库的读连接
        mGoodsHelper.openReadLink();

        //放图片地址的集合
        ArrayList list_path = new ArrayList<>();
        //放标题的集合
        ArrayList list_title = new ArrayList<>();
        list_path.add(R.mipmap.image1);
        list_path.add(R.mipmap.image2);
        list_path.add(R.mipmap.image3);
        list_path.add(R.mipmap.image4);
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱运动");
        list_title.add("不搞对象");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是"是"。
        banner.isAutoPlay(true);
        //设置不能手动影响 默认是手指触摸 轮播图不能翻页
        banner.setViewPagerIsScroll(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
//                .setOnBannerListener((OnBannerListener) getActivity())
                //必须最后调用的方法，启动轮播图。
                .start();

        goodsArray = mGoodsHelper.query("1=1");
        adapter.setNewData(goodsArray);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more:
                showPopupWindow();
                break;
            case R.id.line1://刷新
                refreshLayout.autoRefresh();
                popupWindow.dismiss();
                break;
            case R.id.line2://关于
                Toast.makeText(getActivity(), "关于我们", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                break;
            case R.id.line3://退出
                getActivity().finish();
                popupWindow.dismiss();
                break;
        }
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
//            Glide.with(context).load((String) path).into(imageView);
            imageView.setImageResource((Integer) path);
        }
    }
}