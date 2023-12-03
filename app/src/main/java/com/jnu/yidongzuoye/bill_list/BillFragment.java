package com.jnu.yidongzuoye.bill_list;


import static com.jnu.yidongzuoye.MainActivity.FINISH_TASK_DATA_FILE_NAME;
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
import com.jnu.yidongzuoye.data.DataBankBill;
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

    private void loadBillData(RecyclerView recyclerView,int monthIndex) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        try {
            billList= new DataBankBill().billsInput(requireActivity());
        }
        catch (Exception e)
        {
            billList.add(new Bill("null", "0.0","0.0"));
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
            Double temp = Double.parseDouble(bills.get(position).getBillScore());
            if(temp<0)
            {
                ((BillListayAdapter.ViewHolder) holder).getBillScore().setTextColor(getResources().getColor(R.color.red));
            }
            ((BillListayAdapter.ViewHolder) holder).getBillTime().setText(bills.get(position).getBillTime());
        }
        @Override
        public int getItemCount() {
            return size;
        }
    }

}
