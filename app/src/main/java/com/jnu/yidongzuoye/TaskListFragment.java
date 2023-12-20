package com.jnu.yidongzuoye;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.yidongzuoye.taskdata.commonFragment;
import com.jnu.yidongzuoye.taskdata.dailyFragment;
import com.jnu.yidongzuoye.taskdata.instanceFragment;
import com.jnu.yidongzuoye.taskdata.weeklyFragment;

import java.util.Objects;

public class TaskListFragment extends Fragment {
    public String []tabName={"每天任务","每周任务","普通任务","副本任务"};
    public static ViewPager2 taskViewPager;

    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance() {
        return new TaskListFragment();
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
        TabLayout taskTabLayout = rootView.findViewById(R.id.tabLayout_task);
        taskViewPager=rootView.findViewById(R.id.viewPaGet2_);

        SubAdapter adapter = new SubAdapter(this);
        taskViewPager.setAdapter(adapter);
        // 将子ViewPager与子TabLayout关联
        new TabLayoutMediator(taskTabLayout, taskViewPager,
                (tab, position) -> tab.setText(tabName[position])
        ).attach();
        taskViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 判断是否滑动到边缘
                boolean isScrollingToEdge =  (position == Objects.requireNonNull(taskViewPager.getAdapter()).getItemCount() - 1);
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
        if (currentFragmentIndex < Objects.requireNonNull(taskViewPager.getAdapter()).getItemCount()) {
            taskViewPager.setCurrentItem(currentFragmentIndex);
        }
        else
        {
            MainActivity.viewPager2.setUserInputEnabled(true);
        }

    }
    public class SubAdapter extends FragmentStateAdapter {
        private final int tabsize = tabName.length; // 子Fragment的数量

        public SubAdapter(TaskListFragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // 创建子Fragment并返回
            switch (position) {
                case 0: {
                    return dailyFragment.newInstance();
                }case 1:
                    return weeklyFragment.newInstance();
                case 2:
                    return commonFragment.newInstance();
                default:
                    return instanceFragment.newInstance();
            }

        }
        @Override
        public int getItemCount() {
            return tabsize;
        }
    }
}