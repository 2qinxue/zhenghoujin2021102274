package com.jnu.yidongzuoye;

import static android.app.Activity.RESULT_CANCELED;
import static com.jnu.yidongzuoye.MainActivity.allBills;
import static com.jnu.yidongzuoye.data.DataBankPrize.PRIZE_DATA_FILE_NAME;
import static com.jnu.yidongzuoye.data.DataBankPrize.PRIZE_STORE_DATA_FILE_NAME;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.yidongzuoye.data.Bill;
import com.jnu.yidongzuoye.data.DataBankBill;
import com.jnu.yidongzuoye.data.DataBankPrize;
import com.jnu.yidongzuoye.data.Prize;
import com.jnu.yidongzuoye.prizedata.AddPrizeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class PrizeListFragment extends Fragment {

    private static PrizeCustomAdapter adapter;
    int position;
    public static ArrayList<Prize> prizes = new ArrayList<>();

    private final ArrayList<Prize> prizes_store = new ArrayList<>();

    public static ActivityResultLauncher<Intent> itemLauncher_prize;

    @SuppressLint("StaticFieldLeak")
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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_prize, container, false);
        RecyclerView recycle_view = rootview.findViewById(R.id.recycle_view_prize);
        recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            prizes_store.add(new Prize("游戏30分钟", "-30", "0/30"));
            prizes_store.add(new Prize("看电视30分钟）", "-100", "0/100"));
            prizes_store.add(new Prize("熬夜看剧", "-120", "0/120"));
            prizes_store.add(new Prize("吃麻辣烫", "-20", "0/1"));
            prizes_store.add(new Prize("看小说30分钟", "-30", "0/30"));
            prizes_store.add(new Prize("出去喝酒", "-5", "0/1"));
            prizes_store.add(new Prize("打牌", "-10", "0/10"));
            prizes_store.add(new Prize("旅游", "-30", "0/30"));
            new DataBankPrize().savePrizes(requireActivity(), prizes_store, PRIZE_STORE_DATA_FILE_NAME);
            prizes = new DataBankPrize().prizesInput(requireActivity(), PRIZE_DATA_FILE_NAME);
        } catch (Exception e) {
            prizes.add(new Prize("游戏30分钟", "-30", "0/30"));
            new DataBankPrize().savePrizes(requireActivity(), prizes, PRIZE_DATA_FILE_NAME);
        }
        if (prizes.size() == 0) {
            prizes.add(new Prize("游戏30分钟", "-30", "0/30"));
            new DataBankPrize().savePrizes(requireActivity(), prizes, PRIZE_DATA_FILE_NAME);
        }
        adapter = new PrizeCustomAdapter(prizes);
        recycle_view.setAdapter(adapter);
        registerForContextMenu(recycle_view);
        itemLauncher_prize = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        String name = data.getStringExtra("name");
                        double score_;
                        String score_text = data.getStringExtra("score");
                        try {
                            assert score_text != null;
                            score_ = Double.parseDouble(score_text);// 输入的数据是 double 类型
                        } catch (NumberFormatException e) {
                            score_ = 0.0;   // 输入的数据不是 double 类型
                        }
                        String finish_text = data.getStringExtra("finish_num");
                        prizes.add(new Prize(name, "" + score_,finish_text));
                        adapter.notifyItemInserted(prizes.size());
                        new DataBankPrize().savePrizes(requireActivity(), prizes, PRIZE_DATA_FILE_NAME);
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        Toast.makeText(requireActivity(), "哈哈哈", Toast.LENGTH_SHORT).show();
                    }
                });
        return rootview;
    }
    public class PrizeCustomAdapter extends RecyclerView.Adapter<PrizeCustomAdapter.ViewHolder> {
        private final ArrayList<Prize> prize_item;

        public void removeItem(int position) {
            prize_item.remove(position);
            notifyItemRemoved(position);
        }//删除books
        public class ViewHolder extends RecyclerView.ViewHolder
                implements View.OnCreateContextMenuListener {
            private final TextView prize_name,prizes_score, num;
            private final ImageButton label_button;
            public ViewHolder(View prize_item_view) {
                super(prize_item_view);
                this.prize_name = prize_item_view.findViewById(R.id.textView_daily_prize_name);
                this.prizes_score = prize_item_view.findViewById(R.id.textView_prize_score);
                this.num = prize_item_view.findViewById(R.id.textView_prize_finish);
                this.label_button = prize_item_view.findViewById(R.id.imageButton_label);
                this.label_button.setVisibility(View.GONE);
                prize_item_view.setOnCreateContextMenuListener(this);
            }
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");
                menu.add(0, 0, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "完成" + this.getAdapterPosition());
                menu.add(0, 2, this.getAdapterPosition(), "新建奖励" + this.getAdapterPosition());
                menu.add(0, 3, this.getAdapterPosition(), "添加标签" + this.getAdapterPosition());
                menu.getItem(0).setOnMenuItemClickListener(item -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("删除");
                    builder.setMessage("确定删除？");
                    builder.setPositiveButton("yes", (dialog, which) -> {
                        adapter.removeItem(item.getOrder());
                        new DataBankPrize().savePrizes(requireActivity(), prizes, PRIZE_DATA_FILE_NAME);
                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    });
                    builder.setNegativeButton("no", (dialog, which) -> {
                    });
                    builder.show();
                    return true;
                });
                menu.getItem(1).setOnMenuItemClickListener(item -> {

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("满足奖励");
                    builder.setMessage("确认花费"+prizes.get(item.getOrder()).getScore()+"成就点来满足你的奖励？");
                    builder.setPositiveButton("yes", (dialog, which) -> {
                        Date now = new Date();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = format.format(now);
                        // 获取中文星期几
                        SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE", Locale.CHINA);
                        String dayOfWeek = weekFormat.format(now);

                        try {
                            allBills = new DataBankBill().billsInput(requireActivity());
                        }
                        catch (Exception e){
                            allBills = new ArrayList<>();
                        }
                        allBills.add(new Bill(prizes.get(item.getOrder()).getPrizeName(), prizes.get(item.getOrder()).getScore(), formattedDate+" "+dayOfWeek));
                        new DataBankBill().saveBills(requireActivity(), allBills);
                        adapter.removeItem(item.getOrder());
                        new DataBankPrize().savePrizes(requireActivity(), prizes, PRIZE_DATA_FILE_NAME);
                    });
                    builder.setNegativeButton("no",null);
                    builder.create();
                    builder.show();
                    return true;
                });
                menu.getItem(2).setOnMenuItemClickListener(item -> {
                    Intent intent = new Intent(requireActivity(),
                            AddPrizeActivity.class);
                    itemLauncher_prize.launch(intent);
                    return true;
                });
                menu.getItem(3).setOnMenuItemClickListener(item -> {
                    label_button.setVisibility(View.VISIBLE);
                    return true;
                });
            }
        }
        public PrizeCustomAdapter(ArrayList<Prize> dataSet) {
            prize_item = dataSet;
        }
        @NonNull
        public PrizeCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.prize_detail_list, viewGroup, false);
            return new PrizeCustomAdapter.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.prize_name.setText(prize_item.get(position).getPrizeName());
            holder.prizes_score.setText(prize_item.get(position).getScore());
            holder.num.setText(prize_item.get(position).getNum());
            holder.label_button.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("撤销标签？");
                builder.setPositiveButton("yes", (dialog, which) -> holder.label_button.setVisibility(View.GONE));
                builder.setNegativeButton("no", (dialog, which) -> {});
                builder.show();
            });
        }
        @Override
        public int getItemCount() {
            return prize_item.size();
        }
    }

    static class PrizeComparator implements Comparator<Prize> {
        @Override
        public int compare(Prize przie1, Prize prize2) {
            // 按照score进行升序排序
            return Integer.compare((int) Double.parseDouble(przie1.getScore().substring(1)),(int)Double.parseDouble(prize2.getScore().substring(1)));
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public static void sort_prize() {
        prizes.sort(new PrizeComparator());
        adapter.notifyDataSetChanged(); // 通知适配器数据发生了变化
    }

}
