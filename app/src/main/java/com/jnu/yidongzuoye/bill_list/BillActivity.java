package com.jnu.yidongzuoye.bill_list;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.jnu.yidongzuoye.R;
import com.jnu.yidongzuoye.data.Bill;
import com.jnu.yidongzuoye.data.DataBankBill;

import java.util.ArrayList;
public class BillActivity extends AppCompatActivity implements BillFragment.OnNameUpdateListener {

    private TextView monthText;
    public  TextView dateText;
    private  ViewPager2 billview;
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
    ArrayList<Bill>month_bills;
    public static int currentMonthIndex = 0;
    private final String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    private void initData() {
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
                String text = " ";
                switch (currentMonthIndex) {
                    case 0:
                        if(jan_bill.size()==0||jan_bill.get(0).getBillTime().startsWith("00"))
                            text=months[0];
                        else
                            text=jan_bill.get(0).getBillTime().substring(5,10)+"-"+jan_bill.get(0).getBillTime().substring(20);
                        break;
                    case 1:
                        if(feb_bill.size()==0||feb_bill.get(0).getBillTime().startsWith("00"))
                            text=months[1];
                        else
                            text=feb_bill.get(0).getBillTime().substring(5,10)+"-"+feb_bill.get(0).getBillTime().substring(20);
                        break;
                    case 2:
                        if(mar_bill.size()==0||mar_bill.get(0).getBillTime().startsWith("00"))
                            text=months[2];
                        else
                            text=mar_bill.get(0).getBillTime().substring(5,10)+"-"+mar_bill.get(0).getBillTime().substring(20);
                        break;
                    case 3:
                        if(apr_bill.size()==0||apr_bill.get(0).getBillTime().startsWith("00"))
                            text=months[3];
                        else
                            text=apr_bill.get(0).getBillTime().substring(5,10)+"-"+apr_bill.get(0).getBillTime().substring(20);
                        break;
                    case 4:
                        if(may_bill.size()==0||may_bill.get(0).getBillTime().startsWith("00"))
                            text=months[4];
                        else
                            text=may_bill.get(0).getBillTime().substring(5,10)+"-"+may_bill.get(0).getBillTime().substring(20);
                        break;
                    case 5:
                        if(jun_bill.size()==0|| jun_bill.get(0).getBillTime().startsWith("00"))
                            text=months[5];
                        else
                            text=jun_bill.get(0).getBillTime().substring(5,10)+"-"+jun_bill.get(0).getBillTime().substring(20);
                        break;
                    case 6:
                        if(jul_bill.size()==0|| jul_bill.get(0).getBillTime().startsWith("00"))
                            text=months[6];
                        else
                            text=jul_bill.get(0).getBillTime().substring(5,10)+"-"+jul_bill.get(0).getBillTime().substring(20);
                        break;
                    case 7:
                        if(aug_bill.size()==0|| aug_bill.get(0).getBillTime().startsWith("00"))
                            text=months[7];
                        else
                            text=aug_bill.get(0).getBillTime().substring(5,10)+"-"+aug_bill.get(0).getBillTime().substring(20);
                        break;
                    case 8:
                        if(sep_bill.size()==0|| sep_bill.get(0).getBillTime().startsWith("00"))
                            text=months[8];
                        else
                            text=sep_bill.get(0).getBillTime().substring(5,10)+"-"+sep_bill.get(0).getBillTime().substring(20);
                        break;
                    case 9:
                        if(oct_bill.size()==0|| oct_bill.get(0).getBillTime().startsWith("00"))
                            text=months[9];
                        else
                            text=oct_bill.get(0).getBillTime().substring(5,10)+"-"+oct_bill.get(0).getBillTime().substring(20);
                        break;
                    case 10:
                        if(nov_bill.size()==0|| nov_bill.get(0).getBillTime().startsWith("00"))
                            text=months[10];
                        else
                            text=nov_bill.get(0).getBillTime().substring(5,10)+"-"+nov_bill.get(0).getBillTime().substring(20);
                        break;
                    case 11:
                        if(dec_bill.size()==0|| dec_bill.get(0).getBillTime().startsWith("00"))
                            text=months[11];
                        else
                            text=dec_bill.get(0).getBillTime().substring(5,10)+"-"+dec_bill.get(0).getBillTime().substring(20);
                        break;
                }
                setDateText(text);
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
                    return BillFragment.newInstance(jan_bill);
                case 1:
                    return BillFragment.newInstance(feb_bill);
                case 2:
                    return BillFragment.newInstance(mar_bill);
                case 3:
                    return BillFragment.newInstance(apr_bill);
                case 4:
                    return BillFragment.newInstance(may_bill);
                case 5:
                    return BillFragment.newInstance(jun_bill);
                case 6:
                    return BillFragment.newInstance(jul_bill);
                case 7:
                    return BillFragment.newInstance(aug_bill);
                case 8:
                    return BillFragment.newInstance(sep_bill);
                case 9:
                    return BillFragment.newInstance(oct_bill);
                case 10:
                    return BillFragment.newInstance(nov_bill);
                default:
                    return BillFragment.newInstance(dec_bill);

            }
        }
        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}

