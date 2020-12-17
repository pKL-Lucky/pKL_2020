package com.example.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.bean.UserInfo;
import com.example.shopping.database.UserDBHelper;
import com.example.shopping.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup rg_login; // 声明一个单选组对象
    private RadioButton rb_password; // 声明一个单选按钮对象
    private RadioButton rb_verifycode; // 声明一个单选按钮对象
    private EditText id_phone; // 声明一个编辑框对象
    private TextView tv_password; // 声明一个文本视图对象
    private EditText id_password; // 声明一个编辑框对象
    private Button btn_forget; // 声明一个忘记密码按钮控件对象
    private Button btn_login;
    private Button btn_register;// 声明一个登录按钮控件对象
    private Switch sw_status; // 声明一个复选框对象
    private TextView tv_name; // 声明一个文本视图对象
    private EditText id_name; // 声明一个编辑框对象

    private int mRequestCode = 0; // 跳转页面时的请求代码
    private int mType = 2; // 用户类型
    private boolean bRemember = false; // 是否记住密码
    private String mPassword = "111111"; // 默认密码
    private String mVerifyCode; // 验证码

    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        id_phone = findViewById(R.id.id_phone);
        id_password = findViewById(R.id.id_password);
        id_name=findViewById(R.id.id_name);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);


    }
    @Override
    protected void onResume() {
        super.onResume();
        // 获得用户数据库帮助器的一个实例
        mHelper = UserDBHelper.getInstance(this, 2);
        // 恢复页面，则打开数据库连接
        mHelper.openWriteLink();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 暂停页面，则关闭数据库连接
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){
            UserInfo infocheck = mHelper.queryByPhone(id_phone.getText().toString());
            if (infocheck != null) {
                Toast.makeText(this, "该账户存在", Toast.LENGTH_SHORT).show();
                return;
            }
            String name=id_name.getText().toString();
            String phone = id_phone.getText().toString();
            String password =id_password.getText().toString();
            UserInfo info = new UserInfo();
            info.name=name;
            info.phone = phone;
            info.pwd = password;

            if (phone.length() < 11) { // 手机号码不足11位
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 8) { //密码需要8位
                Toast.makeText(this, "请输入8位密码", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            // 执行数据库帮助器的插入操作
            mHelper.insert(info);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (v.getId() == R.id.login) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}