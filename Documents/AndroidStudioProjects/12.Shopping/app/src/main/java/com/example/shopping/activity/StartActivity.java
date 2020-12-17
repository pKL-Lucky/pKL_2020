package com.example.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.click.guide.guide_lib.GuideCustomViews;
import com.click.guide.guide_lib.interfaces.CallBack;
import com.example.shopping.R;

public class StartActivity extends AppCompatActivity implements CallBack {
    private Handler handler = new Handler();
    private GuideCustomViews guide_CustomView;
    private final int[] mPageImages = {
            R.mipmap.guide_bg1,
            R.mipmap.guide_bg,
            R.mipmap.guide_bg3,
            R.mipmap.guide_bg4,
    };

    private final int[] mGuidePoint = {
            R.mipmap.touming,
            R.mipmap.touming,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);

        initView();
    }

    private void initView() {
        // 隐藏标题栏
        getSupportActionBar().hide();
        guide_CustomView = findViewById(R.id.guide_CustomView);
        guide_CustomView.setData(mPageImages, mGuidePoint, this);
    }

    @Override
    public void callSlidingPosition(int position) {
        Log.e("callSlidingPosition", "滑动位置 callSlidingPosition " + position);
    }

    @Override
    public void callSlidingLast() {
        Log.e("callSlidingLast", "滑动到最后一个callSlidingLast");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        guide_CustomView.clear();
    }
}