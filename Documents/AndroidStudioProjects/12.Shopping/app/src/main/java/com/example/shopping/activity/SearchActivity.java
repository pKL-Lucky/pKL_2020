package com.example.shopping.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.shopping.adapter.SearchAdapter;
import com.example.shopping.bean.CartInfo;
import com.example.shopping.bean.GoodsInfo;
import com.example.shopping.database.CartDBHelper;
import com.example.shopping.database.GoodsDBHelper;
import com.example.shopping.util.DateUtil;
import com.example.shopping.util.SharedUtil;
import com.example.shopping.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private EditText et_search;
    private RecyclerView recyclerView;
    private TextView tv_title;
    private GoodsDBHelper mGoodsHelper;
    private CartDBHelper mCartHelper; // 声明一个购物车数据库的帮助器对象
    private ArrayList<GoodsInfo> goodsArray;
    private ArrayList<GoodsInfo> listData = new ArrayList<>();
    private SearchAdapter adapter;
    private int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initListener();
        initData();
    }

    private void initData() {
        mCount = getIntent().getExtras().getInt("count");
        mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
        // 打开商品数据库的读连接
        mGoodsHelper.openReadLink();
        // 获取购物车数据库的帮助器对象
        mCartHelper = CartDBHelper.getInstance(this, 1);
        // 打开购物车数据库的写连接
        mCartHelper.openWriteLink();

        goodsArray = mGoodsHelper.query("1=1");
    }

    private void initView() {
        et_search = findViewById(R.id.et_search);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("搜索商品");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(R.layout.item_goods2);
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        //点击搜索键的监听
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    SearchActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //实现自己的搜索逻辑
                    for (int i = 0; i < goodsArray.size(); i++) {
                        GoodsInfo goodsInfo = goodsArray.get(i);
                        if (getText().equals(goodsInfo.name)) {
                            listData.add(goodsInfo);
                        }
                    }
                    adapter.setNewData(listData);
                    adapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsInfo info = (GoodsInfo) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.btn_add:
                        addToCart(info.rowid);
                        Toast.makeText(SearchActivity.this,
                                "已添加" + info.name + "到购物车", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 关闭商品数据库的数据库连接
        mGoodsHelper.closeLink();
        // 关闭购物车数据库的数据库连接
        mCartHelper.closeLink();
    }

    public String getText() {
        return et_search.getText().toString().trim();
    }

    // 把指定编号的商品添加到购物车
    private void addToCart(long goods_id) {
        mCount++;
        // 把购物车中的商品数量写入共享参数
        SharedUtil.getIntance(this).writeShared("count", "" + mCount);
        // 根据商品编号查询购物车数据库中的商品记录
        CartInfo info = mCartHelper.queryByGoodsId(goods_id);
        if (info != null) { // 购物车已存在该商品记录
            info.count++; // 该商品的数量加一
            info.update_time = DateUtil.getNowDateTime("");
            // 更新购物车数据库中的商品记录信息
            mCartHelper.update(info);
        } else { // 购物车不存在该商品记录
            info = new CartInfo();
            info.goods_id = goods_id;
            info.count = 1;
            info.update_time = DateUtil.getNowDateTime("");
            // 往购物车数据库中添加一条新的商品记录
            mCartHelper.insert(info);
        }
    }
}