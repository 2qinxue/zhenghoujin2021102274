package com.jnu.yidongzuoye;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.yidongzuoye.data.Bill;
import com.jnu.yidongzuoye.data.DataBankBill;
import com.jnu.yidongzuoye.prizedata.AddPrizeActivity;
import com.jnu.yidongzuoye.taskdata.AddTaskActivity;
import com.jnu.yidongzuoye.taskdata.commonFragment;
import com.jnu.yidongzuoye.taskdata.dailyFragment;
import com.jnu.yidongzuoye.taskdata.instanceFragment;
import com.jnu.yidongzuoye.taskdata.weeklyFragment;
import com.jnu.yidongzuoye.statisticdata.BillActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Bill> allBills = new ArrayList<>();
    public static String FINISH_TASK_DATA_FILE_NAME="finished_tasks.txt";
    public String []TabList={"任务","奖励","统计","我"};
    public static ViewPager2 viewPager2;
    TabLayout tabLayout1;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.viewPaGet2);
        tabLayout1= findViewById(R.id.tabLayout1);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override       // 设置OnPageChangeListener监听器
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Button button = findViewById(R.id.button);
                invalidateOptionsMenu(); // 切换标签页的逻辑// 通知系统重新创建菜单
                if (position == 2) {button.setVisibility(View.VISIBLE);}
                else {button.setVisibility(View.GONE);}
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, BillActivity.class);
                        startActivity(intent);
                    }});}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        MainAdapter mainAdapter = new MainAdapter(this);
        viewPager2.setAdapter(mainAdapter);
        new TabLayoutMediator(tabLayout1, viewPager2, (tab, position) ->
                tab.setText(TabList[(position)])).attach();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int currentTab = viewPager2.getCurrentItem(); // 获取当前选中的标签页索引
        if (currentTab == 0) {
            getMenuInflater().inflate(R.menu.context_menu, menu);
            toolbar.setTitle("任务列表");
        } else if (currentTab == 1) {
            getMenuInflater().inflate(R.menu.prize_menu, menu);
            toolbar.setTitle("奖励列表");
        } else if (currentTab == 2) {
            getMenuInflater().inflate(R.menu.common_menu, menu);
            toolbar.setTitle("时间管理APP");
        }
        return true;
    }
    @SuppressLint({"SuspiciousIndentation", "SetTextI18n"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int currentTab = viewPager2.getCurrentItem(); // 获取当前选中的标签页索引
        int sonTab = TaskListFragment.taskViewPager.getCurrentItem();
        int itemId = item.getItemId();
        if (currentTab == 0) {
            if (itemId == R.id.new_task) {
                Intent intent = new Intent(this, AddTaskActivity.class);
                intent.putExtra("position_tab", sonTab);
                if (0 == sonTab) {
                    dailyFragment.itemLauncher.launch(intent);
                }
                else if(1 == sonTab) {
                    weeklyFragment.itemLauncher.launch(intent);
                }
                else if(2 == sonTab) {
                    commonFragment.itemLauncher.launch(intent);
                }
                else if(3 == sonTab) {
                    instanceFragment.itemLauncher.launch(intent);
                }
                return true;
            } else if (itemId == R.id.add_in_instance) {
                Intent intent = new Intent(this, BillActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.sort_tasks) {
                if (0 == sonTab) {
                    dailyFragment.Sort_daily_task();
                }
                else if(1 == sonTab) {
                    weeklyFragment.Sort_weekly_task();
                }
                else if(2 == sonTab) {
                    commonFragment.Sort_common_task();
                }
                else if(3 == sonTab) {
                    instanceFragment.Sort_instance_task();
                }
                return true;
            }
        } else if (currentTab == 1) {
            if (itemId == R.id.title) {
                Intent intent = new Intent(this, AddPrizeActivity.class);
                PrizeListFragment.itemLauncher_prize.launch(intent);
                return true;
            } else if (itemId == R.id.media_title) {
                double total = 0;
                double comsumption = 0;
                ArrayList<Bill> bills_s;
                try {
                    bills_s=new DataBankBill().billsInput(this);
                }
                catch (Exception e) {
                    bills_s= new ArrayList<>();
                }
                for(Bill bill: bills_s) {
                    if(bill.getBillScore().charAt(0)=='-'){
                        total-=Double.parseDouble( bill.getBillScore().substring(1));
                        comsumption-=Double.parseDouble( bill.getBillScore().substring(1));
                    }
                    else
                        total+=Double.parseDouble( bill.getBillScore().substring(1));
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = LayoutInflater.from(this);
                View dialogView = inflater.inflate(R.layout.achievement, null);
                TextView consumption_text = dialogView.findViewById(R.id.textView5);
                consumption_text.setText(comsumption+"");
                TextView totalmoney = dialogView.findViewById(R.id.textView6);
                totalmoney.setText(total+"");
                builder.setView(dialogView)
                        .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create()
                        .show();
                return true;
                // 处理第二个标签页的菜单项2的点击事件
            } else if (itemId == R.id.label) {
                PrizeListFragment.sort_prize();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public class MainAdapter extends FragmentStateAdapter {
//        private MainActivity mainFragment;
        public MainAdapter(MainActivity fragment) {
            super(fragment);
//            mainFragment = fragment;
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: {
                    return TaskListFragment.newInstance(position);
                }case 1:
                    return PrizeListFragment.newInstance(position);
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
            return TabList.length;
        }
    }


}
