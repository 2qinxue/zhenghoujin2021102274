package com.jnu.myrecycle.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jnu.myrecycle.MainActivity;
import com.jnu.myrecycle.OwnFragment;
import com.jnu.myrecycle.PrizeListFragment;
import com.jnu.myrecycle.R;
import com.jnu.myrecycle.StatisticFragment;
import com.jnu.myrecycle.TaskListFragment;
import com.jnu.myrecycle.bill_list.BillFragment;

public class BillActivity extends AppCompatActivity {

    private TextView monthText;
    ViewPager2 billview;
    private int currentMonthIndex = 0;
    private String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bill_activity_bill);

        billview=findViewById(R.id.billviewPager);

        billview.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentMonthIndex = position;
                updateMonthText();
            }
        });


        BillAdapter mainAdapter = new BillAdapter(BillActivity.this);
        billview.setAdapter(mainAdapter);

        monthText = findViewById(R.id.monthText);
        updateMonthText();
    }

    public void onPreviousButtonClick(View view) {
        if (currentMonthIndex > 0) {
            currentMonthIndex--;
            updateMonthText();
        }
    }

    public void onNextButtonClick(View view) {
        if (currentMonthIndex < months.length - 1) {
            currentMonthIndex++;
            updateMonthText();
        }
    }

    private void updateMonthText() {
        monthText.setText(months[currentMonthIndex]);
    }

    public class BillAdapter extends FragmentStateAdapter {
        private  final int NUM_PAGES = months.length; // Fragment的数量
        private BillActivity billActivity;
        public BillAdapter(BillActivity fragment) {
            super(fragment);
            billActivity = fragment;
        }
        @Override
        public Fragment createFragment(int position) {

            return BillFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}