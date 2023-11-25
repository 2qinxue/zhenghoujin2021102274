package com.jnu.myrecycle.bill_list;

import static com.jnu.myrecycle.data.DataBankTask.FINISH_TASK_DATA_FILE_NAME;
import static com.jnu.myrecycle.data.DataBankTask.TASK_STORE_filename;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jnu.myrecycle.R;
import com.jnu.myrecycle.data.Bill;
import com.jnu.myrecycle.data.DataBankBill;
import java.util.ArrayList;
public class BillFragment extends Fragment {
    private int monthIndex;
    RecyclerView recyclerView;
    BillListayAdapter adapter;
    ArrayList<Bill>billList=new ArrayList<>();

    public static BillFragment newInstance(int monthIndex) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putInt("monthIndex", monthIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        recyclerView=view.findViewById(R.id.billrecyclerView);

        // 获取传递的月份索引
        monthIndex = getArguments().getInt("monthIndex");
        // 加载并展示账单数据
        loadBillData(recyclerView,monthIndex);
        return view;
    }

    private String GetDateText(int monthIndex) {
        String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        return months[monthIndex];
    }

    private void loadBillData(RecyclerView recyclerView,int monthIndex) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        try {
            billList= new DataBankBill().billsInput(requireActivity(),FINISH_TASK_DATA_FILE_NAME);
        }
        catch (Exception e)
        {
            billList.add(new Bill("null", " "," "));
        }
        adapter = new BillListayAdapter(billList);
        recyclerView.setAdapter(adapter);
    }

    public class BillListayAdapter extends RecyclerView.Adapter {
        private int size=billList.size();
        ArrayList<Bill> bills=new ArrayList<>();
        public BillListayAdapter(ArrayList<Bill> billList) {
            bills = billList;
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView billname,billscore,billtime;
            public ViewHolder(View view) {
                super( view);
                billname = view.findViewById(R.id.textView_bill_name);
                billscore = view.findViewById(R.id.textView_bill_score);
                billtime = view.findViewById(R.id.textView_bill_time);
            }
            public TextView getBillName(){
                return billname;
            }
            public TextView getBillScore(){
                return billscore;
            }
            public TextView getBillTime(){
                return billtime;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.bill_detail_list, parent, false);
            return new BillListayAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((BillListayAdapter.ViewHolder) holder).getBillName().setText(bills.get(position).getBillName());
            ((BillListayAdapter.ViewHolder) holder).getBillScore().setText(bills.get(position).getBillScore());
            ((BillListayAdapter.ViewHolder) holder).getBillTime().setText(bills.get(position).getBillTime());
        }
        @Override
        public int getItemCount() {
            return size;
        }
    }

}
