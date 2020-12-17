package com.example.mixture;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TabSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_second);

        // 根据标签栏传来的参数拼接文本字符串
        String desc = String.format("我是%s页面，来自%s",
                "分类", getIntent().getExtras().getString("tag"));
        TextView tv_second = findViewById(R.id.tv_second);
        tv_second.setText(desc);

    }
}