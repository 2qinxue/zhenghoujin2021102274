package com.jnu.yidongzuoye.bill_list;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jnu.yidongzuoye.R;
import com.jnu.yidongzuoye.data.Bill;

import java.util.ArrayList;
public class BillFragment extends Fragment {
    RecyclerView recyclerView;
    BillListayAdapter adapter;
    String[]mon_index={"01","02","03","04","05","06","07","08","09","10","11","12"};
    public interface OnNameUpdateListener {
        void setDateText(String date);
    }
    public static BillFragment newInstance(int monthIndex,ArrayList<Bill>bill) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putSerializable("mon_bill", bill);
        args.putInt("monthIndex", monthIndex);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        recyclerView=view.findViewById(R.id.billrecyclerView);
        ArrayList<Bill>billList;
        // 获取传递的月份索引
        assert getArguments() != null;
        int monthIndex = getArguments().getInt("monthIndex");
        billList= (ArrayList<Bill>) getArguments().getSerializable("mon_bill");
        if(billList.size()==0)
            billList.add(new Bill("暂无账单","00","0000-00-00 00:00:00 000"));
        // 加载并展示账单数据
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new BillListayAdapter(billList);
        recyclerView.setAdapter(adapter);

        if (getActivity() instanceof OnNameUpdateListener) {
            BillActivity.billview.setCurrentItem(monthIndex, false); // 添加这行代码
            OnNameUpdateListener listener = (OnNameUpdateListener) getActivity();
            if(billList.get(0).getBillTime().startsWith("00"))
                listener.setDateText(mon_index[monthIndex]+"月");
            else
                listener.setDateText(billList.get(0).getBillTime().substring(5,10)+"-"+billList.get(0).getBillTime().substring(20));
        }
        return view;
    }


    public class BillListayAdapter extends RecyclerView.Adapter {
        ArrayList<Bill> bills;
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
            double temp = Double.parseDouble(bills.get(position).getBillScore());
            if(temp<0)
            {
                ((BillListayAdapter.ViewHolder) holder).getBillScore().setTextColor(Color.RED);
            }
            ((BillListayAdapter.ViewHolder) holder).getBillTime().setText(bills.get(position).getBillTime().substring(11,18));
        }
        @Override
        public int getItemCount() {
            return bills.size();
        }
    }
}

