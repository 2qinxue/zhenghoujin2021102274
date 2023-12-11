package com.jnu.yidongzuoye.bill_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.jnu.yidongzuoye.R;
import com.jnu.yidongzuoye.data.Bill;
import com.jnu.yidongzuoye.data.DataBankBill;
import java.util.ArrayList;
public class BillActivity extends AppCompatActivity implements BillFragment.OnNameUpdateListener {

    private TextView monthText;
    public  TextView dateText;
    public static ViewPager2 billview;
    BillAdapter mainAdapter;
    ArrayList<Bill>jan_bill=new ArrayList<>();
    ArrayList<Bill>feb_bill=new ArrayList<>();
    ArrayList<Bill>mar_bill=new ArrayList<>();
    ArrayList<Bill>apr_bill=new ArrayList<>();
    ArrayList<Bill>may_bill=new ArrayList<>();
    ArrayList<Bill>jun_bill=new ArrayList<>();
    ArrayList<Bill>jul_bill=new ArrayList<>();
    ArrayList<Bill>aug_bill=new ArrayList<>();
    ArrayList<Bill>sep_bill=new ArrayList<>();
    ArrayList<Bill>oct_bill=new ArrayList<>();
    ArrayList<Bill>nov_bill=new ArrayList<>();
    ArrayList<Bill>dec_bill=new ArrayList<>();

    String[]mon_index={"01","02","03","04","05","06","07","08","09","10","11","12"};
    private int currentMonthIndex = 0;
    private final String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    private void initData() {
        ArrayList<Bill>month_bills;
        try{
            month_bills= new DataBankBill().billsInput(this);
        }
        catch (Exception e){
            month_bills=new ArrayList<>();
        }
        for(int i=0;i<month_bills.size();i++){
            switch (month_bills.get(i).getBillTime().substring(5,7)){
                case "01":
                    jan_bill.add(month_bills.get(i));
                    break;
                case "02":
                    feb_bill.add(month_bills.get(i));
                    break;
                case "03":
                    mar_bill.add(month_bills.get(i));
                    break;
                case "04":
                    apr_bill.add(month_bills.get(i));
                    break;
                case "05":
                    may_bill.add(month_bills.get(i));
                    break;
                case "06":
                    jun_bill.add(month_bills.get(i));
                    break;
                case "07":
                    jul_bill.add(month_bills.get(i));
                    break;
                case "08":
                    aug_bill.add(month_bills.get(i));
                    break;
                case "09":
                    sep_bill.add(month_bills.get(i));
                    break;
                case "10":
                    oct_bill.add(month_bills.get(i));
                    break;
                case "11":
                    nov_bill.add(month_bills.get(i));
                    break;
                case "12":
                    dec_bill.add(month_bills.get(i));
                    break;
            }
        }
    }
    public  void setDateText(String date)
    {
        dateText.setText(date);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.bill_activity_bill);
        billview = findViewById(R.id.billviewPager);
        dateText = findViewById(R.id.textView_date);
        monthText = findViewById(R.id.monthText);
        mainAdapter = new BillAdapter(BillActivity.this);
        billview.setAdapter(mainAdapter);
        billview.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // 在页面选中时的回调方法
                super.onPageSelected(position);
                currentMonthIndex = position;
                monthText.setText(months[currentMonthIndex]);
                setDateText(mon_index[currentMonthIndex] + "月");
                billview.setCurrentItem(currentMonthIndex, false); // 添加这行代码
            }
        });
    }

    public void onPreviousButtonClick(View view) {
        if (currentMonthIndex > 0) {
            currentMonthIndex--;
            billview.setCurrentItem(currentMonthIndex, false); // 添加这行代码
        }
    }
    public void onNextButtonClick(View view) {
        if (currentMonthIndex < months.length - 1) {
            currentMonthIndex++;
            billview.setCurrentItem(currentMonthIndex, false); // 添加这行代码
        }
    }
    public class BillAdapter extends FragmentStateAdapter {
        private  final int NUM_PAGES = months.length; // Fragment的数量
        public BillAdapter(BillActivity billactivityfragment) {
            super(billactivityfragment);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {

            switch (position) {
                case 0:
                    return BillFragment.newInstance(position, jan_bill);
                case 1:
                    return BillFragment.newInstance(position, feb_bill);
                case 2:
                    return BillFragment.newInstance(position, mar_bill);
                case 3:
                    return BillFragment.newInstance(position, apr_bill);
                case 4:
                    return BillFragment.newInstance(position, may_bill);
                case 5:
                    return BillFragment.newInstance(position, jun_bill);
                case 6:
                    return BillFragment.newInstance(position, jul_bill);
                case 7:
                    return BillFragment.newInstance(position, aug_bill);
                case 8:
                    return BillFragment.newInstance(position, sep_bill);
                case 9:
                    return BillFragment.newInstance(position, oct_bill);
                case 10:
                    return BillFragment.newInstance(position, nov_bill);
                case 11:
                    return BillFragment.newInstance(position, dec_bill);
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

