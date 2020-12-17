package com.example.shopping.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopping.R;
import com.example.shopping.fragment.CassiFicationFragment;
import com.example.shopping.fragment.HomePageFragment;
import com.example.shopping.fragment.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {
    public static HomePageActivity activity;
    private BottomNavigationView bottom_ngs;
    private int index = 0;
    private String fragmentTag;
    private ArrayList<String> fragmentNames;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        activity = this;
        initView();
    }

    private void initView() {
        // 隐藏标题栏
        getSupportActionBar().hide();
        bottom_ngs = findViewById(R.id.bottom_ngs);
        fragmentNames = new ArrayList<>();
        fragmentNames.add(HomePageFragment.class.getName());
        fragmentNames.add(CassiFicationFragment.class.getName());
        fragmentNames.add(ShoppingCartFragment.class.getName());

        fragmentTag = fragmentNames.get(1);
        Fragment fragment2 = getFragmentByTag(fragmentTag);
        showFragment(mCurrentFragment, fragment2, fragmentTag);

        fragmentTag = fragmentNames.get(index);
        Fragment fragment = getFragmentByTag(fragmentTag);
        showFragment(mCurrentFragment, fragment, fragmentTag);

        initListener();
    }

    private void initListener() {
        bottom_ngs.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.homeFragment) {
                    index = 0;
                } else if (menuItem.getItemId() == R.id.classiFication) {
                    index = 1;
                } else if (menuItem.getItemId() == R.id.shoppingCart) {
                    index = 2;
                }
                fragmentTag = fragmentNames.get(index);
                Fragment fragment = getFragmentByTag(fragmentTag);
                showFragment(mCurrentFragment, fragment, fragmentTag);

                return true;
            }
        });
    }

    public void change(int num) {
        fragmentTag = fragmentNames.get(num);
        Fragment fragment = getFragmentByTag(fragmentTag);
        showFragment(mCurrentFragment, fragment, fragmentTag);
        bottom_ngs.setSelectedItemId(bottom_ngs.getMenu().getItem(num).getItemId());
    }

    private Fragment getFragmentByTag(String name) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(name);
        if (fragment != null) {
            return fragment;
        } else {
            try {
                fragment = (Fragment) Class.forName(name).newInstance();
            } catch (Exception e) {
                // fragment = new HomeFragment();
            }
        }
        return fragment;
    }

    private void showFragment(Fragment from, Fragment to, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from == null) {
            if (to.isAdded()) {
                transaction.show(to);
            } else {
                transaction.add(R.id.fragment_nrs, to, tag);
            }
        } else {
            if (to.isAdded()) {
                transaction.hide(from).show(to);
            } else {
                transaction.hide(from).add(R.id.fragment_nrs, to, tag);
            }
        }
        transaction.commitAllowingStateLoss();
        mCurrentFragment = to;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}