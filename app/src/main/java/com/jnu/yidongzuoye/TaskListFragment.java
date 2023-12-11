package com.jnu.yidongzuoye;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.yidongzuoye.taskdata.AddTaskActivity;
import com.jnu.yidongzuoye.taskdata.commonFragment;
import com.jnu.yidongzuoye.taskdata.dailyFragment;
import com.jnu.yidongzuoye.taskdata.instanceFragment;
import com.jnu.yidongzuoye.taskdata.weeklyFragment;
public class TaskListFragment extends Fragment {
    public String []tabName={"每天任务","每周任务","普通任务","副本任务"};
    private TabLayout taskTabLayout;
    public static ViewPager2 taskViewPager;
    private int position;
    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance(int position) {
        TaskListFragment fragment = new TaskListFragment();
        fragment.position = position;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_task_list, container, false);
        taskTabLayout=rootView.findViewById(R.id.tabLayout_task);
        taskViewPager=rootView.findViewById(R.id.viewPaGet2_);


        SubAdapter adapter = new SubAdapter(this);
        taskViewPager.setAdapter(adapter);
        // 将子ViewPager与子TabLayout关联
        new TabLayoutMediator(taskTabLayout, taskViewPager,
                (tab, position) -> {tab.setText(tabName[position]);}
        ).attach();
        taskViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 判断是否滑动到边缘
                boolean isScrollingToEdge =  (position == taskViewPager.getAdapter().getItemCount() - 1);
                // 如果滑动到边缘，则请求父 ViewPager2 不要拦截滑动事件
                MainActivity.viewPager2.setUserInputEnabled(isScrollingToEdge);
            }

        });
        return rootView;
    }
    public void scrollToNextFragment() {
        // 获取当前显示的子 Fragment 的索引
        int currentFragmentIndex = taskViewPager.getCurrentItem();
        // 切换到下一个子 Fragment
        if (currentFragmentIndex < taskViewPager.getAdapter().getItemCount()) {
            taskViewPager.setCurrentItem(currentFragmentIndex);
        }
        else
        {
            MainActivity.viewPager2.setUserInputEnabled(true);
        }

    }
    public class SubAdapter extends FragmentStateAdapter {
        private int tabsize = tabName.length; // 子Fragment的数量

        private TaskListFragment subFragment;

        public SubAdapter(TaskListFragment fragment) {
            super(fragment);
            subFragment = fragment;
        }

        @Override
        public Fragment createFragment(int position) {
            // 创建子Fragment并返回
            switch (position) {
                case 0: {
                    return dailyFragment.newInstance(subFragment.position, position);
                }case 1:
                    return weeklyFragment.newInstance(subFragment.position, position);
                case 2:
                    return commonFragment.newInstance(subFragment.position, position);
                case 3:
                    return instanceFragment.newInstance(subFragment.position, position);
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