package com.jnu.myrecycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.myrecycle.data.Task;
import com.jnu.myrecycle.statisticdata.StatisticdailyFragment;
import com.jnu.myrecycle.statisticdata.StatisticmonthlyFragment;
import com.jnu.myrecycle.statisticdata.StatisticweeklyFragment;
import com.jnu.myrecycle.statisticdata.StatisticyearlyFragment;
import com.jnu.myrecycle.taskdata.commonFragment;
import com.jnu.myrecycle.taskdata.instanceFragment;
import com.jnu.myrecycle.taskdata.weeklyFragment;

import java.util.ArrayList;

public class StatisticFragment extends Fragment {

    private ArrayList<Task> tasks = new ArrayList<>();
    public String []statistic_list_Name={"日","周","月","年"};
    private int position;

    public int dateposition=0;
    private ViewPager2 statisticViewPager;

    private TabLayout statisticTabLayout;
    public StatisticFragment() {

    }
    public static StatisticFragment newInstance(int position) {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();
        fragment.position = position;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
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
        View rootView =inflater.inflate(R.layout.fragment_statistic, container, false);

        statisticTabLayout = rootView.findViewById(R.id.statistictabLayout);
        statisticViewPager=rootView.findViewById(R.id.statisticViewpager);

        /////////////////////////////////////////////////////////////////////////
        // 创建子Adapter并设置给子ViewPager
        StatisticSubAdapter adapter = new StatisticSubAdapter(this);
        statisticViewPager.setAdapter(adapter);

        // 将子ViewPager与子TabLayout关联
        new TabLayoutMediator(statisticTabLayout, statisticViewPager,
                (tab, position) -> {
                    // 设置子TabLayout的标题
                    tab.setText(statistic_list_Name[position]);
                }
        ).attach();
        ///////////////////////////////////////////////////////////////////////////
        return rootView;
    }

    public class StatisticSubAdapter extends FragmentStateAdapter {
        private int tabsize = statistic_list_Name.length; // 子Fragment的数量

        private StatisticFragment substatisticFragment;

        public StatisticSubAdapter(StatisticFragment fragment) {
            super(fragment);
            substatisticFragment = fragment;
        }

        @Override
        public Fragment createFragment(int position) {
            // 创建子Fragment并返回
            switch (position) {
                case 0: {
                    return StatisticdailyFragment.newInstance(substatisticFragment.position, position);
                }case 1:
                    return StatisticweeklyFragment.newInstance(substatisticFragment.position, position);
                case 2:
                    return StatisticmonthlyFragment.newInstance(substatisticFragment.position, position);
                case 3:
                    return StatisticyearlyFragment.newInstance(substatisticFragment.position, position);
                default:
                    return null;
            }
        }
        @Override
        public int getItemCount() {
            return tabsize;
        }
    }
}