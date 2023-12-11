package com.jnu.yidongzuoye;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.yidongzuoye.data.Bill;
import com.jnu.yidongzuoye.data.DataBankBill;
import com.jnu.yidongzuoye.statisticdata.StatisticdailyFragment;
import com.jnu.yidongzuoye.statisticdata.StatisticmonthlyFragment;
import com.jnu.yidongzuoye.statisticdata.StatisticweeklyFragment;
import com.jnu.yidongzuoye.statisticdata.StatisticyearlyFragment;
import com.jnu.yidongzuoye.view.CustomCurveChart;
import com.jnu.yidongzuoye.view.IncomBIll;
import com.jnu.yidongzuoye.view.Statistic;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StatisticFragment extends Fragment {
    public static String[] statistic_list_Name = {"日", "周", "月", "年"};
    public static boolean isRefresh = true;
    private ViewPager2 statisticViewPager;
    private TabLayout statisticTabLayout;
    int tab_onSelect_position;

    public StatisticFragment() {

    }

    public static StatisticFragment newInstance(int position) {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // 清除菜单项
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_statistic, container, false);

        statisticTabLayout = rootView.findViewById(R.id.statistictabLayout);
        statisticViewPager = rootView.findViewById(R.id.statisticViewpager);
        statisticViewPager.setOffscreenPageLimit(1);
        // 创建子Adapter并设置给子ViewPager
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(0, StatisticdailyFragment.newInstance(0));
        fragmentList.add(1, StatisticweeklyFragment.newInstance(1));
        fragmentList.add(2, StatisticmonthlyFragment.newInstance(2));
        fragmentList.add(3, StatisticyearlyFragment.newInstance(3));
        StatisticSubAdapter adapter = new StatisticSubAdapter(this, fragmentList);
        statisticViewPager.setAdapter(adapter);

        // 将子ViewPager与子TabLayout关联
        new TabLayoutMediator(statisticTabLayout, statisticViewPager,
                (tab, position) -> {
                    tab.setText(statistic_list_Name[position]);
                }).attach();
        statisticTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 当Tab被选中时触发
                tab_onSelect_position = tab.getPosition();
                // 根据选中的位置执行相应的操作
                IncomBIll.currentImcomposition = tab_onSelect_position;
                CustomCurveChart.currentCustomposition = tab_onSelect_position;
                Statistic.currentStatisticposition = tab_onSelect_position;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 当选项卡被重新选中时执行的操作
                tab_onSelect_position = tab.getPosition();
                // 根据选中的位置执行相应的操作
                IncomBIll.currentImcomposition = tab_onSelect_position;
                CustomCurveChart.currentCustomposition = tab_onSelect_position;
                Statistic.currentStatisticposition = tab_onSelect_position;
            }
        });
        return rootView;
    }

    public void scrollToNextFragment() {
        // 获取当前显示的子 Fragment 的索引
        int currentFragmentIndex = statisticViewPager.getCurrentItem();
        // 切换到下一个子 Fragment
        if (currentFragmentIndex < statisticViewPager.getAdapter().getItemCount()) {
            statisticViewPager.setCurrentItem(currentFragmentIndex);
        }
    }

    public  class StatisticSubAdapter extends FragmentStateAdapter {
        private final int tabsize = statistic_list_Name.length; // 子Fragment的数量
        public ArrayList<Fragment> fragment_List;

        public StatisticSubAdapter(StatisticFragment fragment, ArrayList<Fragment> fragmentList) {
            super(fragment);
            this.fragment_List = fragmentList;
        }

        @NonNull
        public Fragment createFragment(int position) {
            return fragment_List.get(position);
        }

        @Override
        public int getItemCount() {
            return tabsize;
        }
    }

}

