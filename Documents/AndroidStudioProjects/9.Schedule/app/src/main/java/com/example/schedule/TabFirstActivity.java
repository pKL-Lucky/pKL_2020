package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TabFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_first);

        // 根据标签栏传来的参数拼接文本字符串
        String desc = String.format("我是%s页面，来自%s",
                "首页", getIntent().getExtras().getString("tag"));
        TextView tv_first = findViewById(R.id.tv_first);
        Log.d("TabFirstActivity", "getId=" + tv_first.getId());
        tv_first.setText(desc);
    }
}