package com.jnu.myrecycle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.myrecycle.view.BillActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {


    public String []TabList={"任务","奖励","统计","我"};
    ViewPager2 viewPager2;
    TabLayout tabLayout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.viewPaGet2);
        tabLayout1= findViewById(R.id.tabLayout1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override       // 设置OnPageChangeListener监听器
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Button button = findViewById(R.id.button);
                switchTab(position);
                // 当页面切换到第三个页面时，显示按钮；其他页面隐藏按钮
                if (position == 2)
                {button.setVisibility(View.VISIBLE);}
                else
                {
                    button.setVisibility(View.GONE);
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, BillActivity.class);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        MainAdapter mainAdapter = new MainAdapter(this);
        viewPager2.setAdapter(mainAdapter);
        new TabLayoutMediator(tabLayout1, viewPager2, (tab, position) ->
                tab.setText(TabList[(position)])).attach();
    }

    // 在切换标签页时调用
    private void switchTab(int tabPosition) {
        // 切换标签页的逻辑
        // ...
        // 通知系统重新创建菜单
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int currentTab = viewPager2.getCurrentItem(); // 获取当前选中的标签页索引
        if (currentTab == 0) {
            getMenuInflater().inflate(R.menu.context_menu, menu);
        } else if (currentTab == 1) {
            getMenuInflater().inflate(R.menu.prize_menu, menu);
        } else if (currentTab == 2) {
            getMenuInflater().inflate(R.menu.common_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int currentTab = viewPager2.getCurrentItem(); // 获取当前选中的标签页索引
        int itemId = item.getItemId();
        if (currentTab == 0) {
            if (itemId == R.id.new_task) {
                Toast.makeText(this, "新建任务", Toast.LENGTH_SHORT).show();
                // 处理第一个标签页的菜单项1的点击事件
                return true;
            } else if (itemId == R.id.add_in_instance) {
                Toast.makeText(this, "添加到实例", Toast.LENGTH_SHORT).show();
                // 处理第一个标签页的菜单项2的点击事件
                return true;
            } else if (itemId == R.id.sort_tasks) {
                Toast.makeText(this, "排序任务", Toast.LENGTH_SHORT).show();
                // 处理第一个标签页的菜单项3的点击事件
                return true;
            }
        } else if (currentTab == 1) {
            if (itemId == R.id.title) {
                Toast.makeText(this, "奖励", Toast.LENGTH_SHORT).show();
                // 处理第二个标签页的菜单项1的点击事件
                return true;
            } else if (itemId == R.id.media_title) {
                Toast.makeText(this, "统计", Toast.LENGTH_SHORT).show();
                // 处理第二个标签页的菜单项2的点击事件
                return true;
            } else if (itemId == R.id.label) {
                Toast.makeText(this, "我", Toast.LENGTH_SHORT).show();
                // 处理第二个标签页的菜单项3的点击事件
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    //////////////////////////////////////////////////////////////////////////////
    public class MainAdapter extends FragmentStateAdapter {
        private static final int NUM_PAGES = 4; // Fragment的数量
        private MainActivity mainFragment;
        public MainAdapter(MainActivity fragment) {
            super(fragment);
            mainFragment = fragment;
        }
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: {
                    return TaskListFragment.newInstance(position);
                }case 1:
                    return new PrizeListFragment();
                case 2:
                    return StatisticFragment.newInstance(position);
                case 3:
                    return new OwnFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
