package com.jnu.myrecycle;

import static com.jnu.myrecycle.data.DataBankPrize.PRIZE_DATA_FILE_NAME;
import static com.jnu.myrecycle.data.DataBankTask.FINISH_TASK_DATA_FILE_NAME;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.myrecycle.data.DataBankPrize;
import com.jnu.myrecycle.data.DataBankTask;
import com.jnu.myrecycle.data.Prize;
import com.jnu.myrecycle.data.Task;
import com.jnu.myrecycle.taskdata.dailyFragment;

import java.util.ArrayList;

public class PrizeListFragment extends Fragment {

    private PrizeCustomAdapter adapter;


    int position;
    private ArrayList<Prize>prizes = new ArrayList<>();
    public PrizeListFragment() {
        // Required empty public constructor
    }
    public static PrizeListFragment newInstance(int Position) {
        PrizeListFragment fragment = new PrizeListFragment();
        Bundle args = new Bundle();
        fragment.position = Position;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_prize, container, false);
        RecyclerView recycle_view = rootview.findViewById(R.id.recycle_view_prize);
        recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        prizes.add(new Prize("游戏30分钟", "-30", "0/30"));
        prizes.add(new Prize("专业阅读30分钟）", "-100", "0/100"));
        prizes.add(new Prize("Keep 课程一次", "-120", "0/120"));
        prizes.add(new Prize("深蹲一套 ", "-2", "0/1"));
        prizes.add(new Prize("学习视频30分钟", "-30", "0/30"));
        prizes.add(new Prize("早睡", "-5", "0/1"));
        prizes.add(new Prize("背10个单词", "-10", "0/10"));
        prizes.add(new Prize("跑步30分钟", "-30", "0/30"));
        adapter = new PrizeCustomAdapter(prizes);
        recycle_view.setAdapter(adapter);
        return rootview;
    }



    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case 0: {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("删除");
                builder.setMessage("确定删除？");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = item.getOrder();
                        adapter.removeItem(position);
                        new DataBankPrize().savePrizes(requireActivity(), prizes,PRIZE_DATA_FILE_NAME);
                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                break;
            }
            case 1:
            {
                new DataBankPrize().savePrizes(requireActivity(),prizes,FINISH_TASK_DATA_FILE_NAME);
                adapter.removeItem(item.getOrder());
                new DataBankPrize().savePrizes(requireActivity(),prizes,PRIZE_DATA_FILE_NAME);
            }


            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    public class PrizeCustomAdapter extends  RecyclerView.Adapter<PrizeListFragment.PrizeCustomAdapter.ViewHolder>{
        private ArrayList<Prize> prize_item;

        public void removeItem(int position) {
            prize_item.remove(position);
            notifyItemRemoved(position);
        }//删除books

        public void editItem(int position, String newName, String newScore) {
            Prize prize = prize_item.get(position);
            prize.setPrizeName(newName);
            prize.setScore(newScore);
            notifyItemChanged(position);
        }
        public class ViewHolder extends RecyclerView.ViewHolder
                implements View.OnCreateContextMenuListener {
            private TextView prize_name;
            private  TextView prizes_score;
            private TextView num;
            public ViewHolder(View prize_item_view) {
                super(prize_item_view);
                this.prize_name = prize_item_view.findViewById(R.id.textView_daily_prize_name);
                this.prizes_score = prize_item_view.findViewById(R.id.textView_prize_score);
                this.num = prize_item_view.findViewById(R.id.textView_prize_finish);
                prize_item_view.setOnCreateContextMenuListener(this);
            }
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
                menu.setHeaderTitle("具体操作");
                menu.add(0,0,this.getAdapterPosition(),"删除"+this.getAdapterPosition());
                menu.add(0,1,this.getAdapterPosition(),"完成"+this.getAdapterPosition());
            }
            public TextView getPrize_Name(){
                return prize_name;
            }
            public TextView getPrize_Score(){
                return prizes_score;
            }
            public TextView getPrize_Finish(){return num;}
        }
        public PrizeCustomAdapter(ArrayList<Prize> dataSet) {
            prize_item = dataSet;
        }

        public PrizeCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.prize_detail_list, viewGroup, false);
            return new PrizeCustomAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getPrize_Name().setText(prize_item.get(position).getPrizeName());
            holder.getPrize_Score().setText(prize_item.get(position).getScore());
            holder.getPrize_Finish().setText(prize_item.get(position).getNum());
        }
        @Override
        public int getItemCount() {
            return prize_item.size();
        }
    }

}