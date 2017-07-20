package com.lanbo.daza.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lanbo.daza.R;
import com.lanbo.daza.ui.fragments.Fragment1;
import com.lanbo.daza.ui.fragments.Fragment2;
import com.lanbo.daza.ui.fragments.Fragment3;
import com.lanbo.daza.ui.fragments.Fragment4;
import com.lanbo.daza.view.BottomTabView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = MainActivity2.class.getSimpleName();
    BottomTabView bottomTabView;

    ViewPager viewPager;

    FragmentPagerAdapter adapter;

    ArrayList<Fragment> fragments = new ArrayList<>();

    ArrayList<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(6);
        bottomTabView = (BottomTabView) findViewById(R.id.bottomTabView);

        fragments = new ArrayList<>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewPager.setAdapter(adapter);

        tabItemViews.add(new BottomTabView.TabItemView(this, "蜜拓蜜", R.color.color_c4c4c4, R.color.color_009999, R.drawable.ic_home_black_24dp, R.drawable.ic_home_green_24dp));
        tabItemViews.add(new BottomTabView.TabItemView(this, "金币", R.color.color_c4c4c4, R.color.color_009999, R.drawable.ic_account_balance_wallet_black_24dp, R.drawable.ic_account_balance_wallet_green_24dp));
        tabItemViews.add(new BottomTabView.TabItemView(this, "购物车", R.color.color_c4c4c4, R.color.color_009999, R.drawable.ic_shopping_cart_black_24dp, R.drawable.ic_shopping_cart_green_24dp));
        tabItemViews.add(new BottomTabView.TabItemView(this, "我的", R.color.color_c4c4c4, R.color.color_009999, R.drawable.ic_person_black_24dp, R.drawable.ic_person_green_24dp));

        bottomTabView.setTabItemViews(tabItemViews);

        bottomTabView.setUpWithViewPager(viewPager);

    }

}