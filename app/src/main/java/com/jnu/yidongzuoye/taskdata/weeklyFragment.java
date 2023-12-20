package com.jnu.yidongzuoye.taskdata;

import static android.app.Activity.RESULT_CANCELED;
import static com.jnu.yidongzuoye.MainActivity.allBills;
import static com.jnu.yidongzuoye.data.DataBankTask.DAILY_TASK_DATA_FILE_NAME;
import static com.jnu.yidongzuoye.data.DataBankTask.WEEKLY_TASK_FILE_NAME;
import static com.jnu.yidongzuoye.data.DataBankTask.WEEKLY_TASK_STORE_FILENAME;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.yidongzuoye.R;
import com.jnu.yidongzuoye.data.Bill;
import com.jnu.yidongzuoye.data.DataBankBill;
import com.jnu.yidongzuoye.data.DataBankTask;
import com.jnu.yidongzuoye.data.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class weeklyFragment extends Fragment {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private final ArrayList<Task> tasks_store = new ArrayList<>();
    public static ActivityResultLauncher<Intent> itemLauncher;
    private static WeeklyCustomAdapter adapter;
    public weeklyFragment() {
        // Required empty public constructor
    }
    public static weeklyFragment newInstance() {
        return new weeklyFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // View rootView = inflater.inflate(R.layout.fragment_weekly, container, false);
        View rootView = inflater.inflate(R.layout.fragment_taskweekly, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycle_view_task_weekly);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        try{
            tasks_store.add(new Task("普通阅读100分钟", "+150", "0/100"));
            tasks_store.add(new Task("专业阅读200分钟）", "+200", "0/200"));
            tasks_store.add(new Task("Keep 课程5次", "+200", "0/5"));
            tasks_store.add(new Task("深蹲10套 ", "+20", "0/10"));
            tasks_store.add(new Task("学习视频200分钟", "+100", "0/200"));
            tasks_store.add(new Task("早睡5次", "+50", "0/5"));
            tasks_store.add(new Task("背100个单词", "+30", "0/100"));
            tasks_store.add(new Task("跑步300分钟", "+100", "0/300"));
            new DataBankTask().saveTasks(requireActivity().getApplicationContext(), tasks_store,WEEKLY_TASK_STORE_FILENAME);
            tasks=new DataBankTask().tasksInput(requireActivity(),WEEKLY_TASK_FILE_NAME);
        }
        catch (Exception e){
            tasks.add(new Task("普通阅读100分钟", "+150", "0/150"));
            new DataBankTask().saveTasks(requireActivity(), tasks,WEEKLY_TASK_FILE_NAME);
        }

        if(0 == tasks.size()){
            tasks.add(new Task("普通阅读100分钟", "+150", "0/150"));
        }
        adapter = new WeeklyCustomAdapter(tasks);
        recyclerView.setAdapter(adapter);
        itemLauncher = registerForActivityResult(
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
                        tasks.add(new Task(name, "+" + score_,finish_text));
                        adapter.notifyItemInserted(tasks.size());
                        new DataBankTask().saveTasks(requireActivity(), tasks, WEEKLY_TASK_FILE_NAME);
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        Toast.makeText(requireActivity(), "哈哈哈", Toast.LENGTH_SHORT).show();
                    }
                });
        return rootView;
    }
    public class WeeklyCustomAdapter extends  RecyclerView.Adapter<WeeklyCustomAdapter.ViewHolder>{
        private final ArrayList<Task> task_item;

        public void removeItem(int position) {
            task_item.remove(position);
            notifyItemRemoved(position);
        }//删除books

        public class ViewHolder extends RecyclerView.ViewHolder
                implements View.OnCreateContextMenuListener {
            private final CheckBox checkBox;
            private final TextView task_name;
            private final TextView tasks_score;
            private final TextView num;
            public ViewHolder(View task_item_view) {
                super(task_item_view);

                this.task_name = task_item_view.findViewById(R.id.textView_daily_task_name);
                this.tasks_score = task_item_view.findViewById(R.id.textView_daily_score);
                this.num = task_item_view.findViewById(R.id.textView_daily_finish);
                checkBox = task_item_view.findViewById(R.id.checkBox);
                checkBox.setChecked(false);
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        try {
                            allBills = new DataBankBill().billsInput(requireActivity());
                        }
                        catch (Exception e){
                            allBills = new ArrayList<>();
                        }
                        Date now = new Date();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = format.format(now);
                        // 获取中文星期几
                        SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE", Locale.CHINA);
                        String dayOfWeek = weekFormat.format(now);
                        allBills.add(new Bill((String) task_name.getText(), (String) tasks_score.getText(), formattedDate+" "+ dayOfWeek));
                        new DataBankBill().saveBills(requireActivity(), allBills);
                        int position = (int) buttonView.getTag(); // 获取位置信息
                        // 其他逻辑处理...
                        adapter.removeItem(position);
                        new DataBankTask().saveTasks(requireActivity(), tasks, DAILY_TASK_DATA_FILE_NAME);
                    } else {
                        buttonView.setChecked(false);
                    }
                });
                task_item_view.setOnCreateContextMenuListener(this);
            }
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
                menu.setHeaderTitle("具体操作");
                menu.add(0,0,this.getAdapterPosition(),"删除"+this.getAdapterPosition());
                menu.add(0,1,this.getAdapterPosition(),"完成"+this.getAdapterPosition());
                menu.add(0,2,this.getAdapterPosition(),"新建"+this.getAdapterPosition());
                menu.getItem(0).setOnMenuItemClickListener(item -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("删除");
                    builder.setMessage("确定删除？");
                    builder.setPositiveButton("yes", (dialog, which) -> {
                        adapter.removeItem(item.getOrder());
                        new DataBankTask().saveTasks(requireActivity(), tasks, WEEKLY_TASK_FILE_NAME);
                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    });
                    builder.setNegativeButton("no", (dialog, which) -> {
                    });
                    builder.show();
                    return true;
                });
                menu.getItem(1).setOnMenuItemClickListener(item -> {

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
                    allBills.add(new Bill(tasks.get(item.getOrder()).getTaskName(), tasks.get(item.getOrder()).getScore(), formattedDate+" "+dayOfWeek));
                    new DataBankBill().saveBills(requireActivity(), allBills);
                    adapter.removeItem(item.getOrder());
                    new DataBankTask().saveTasks(requireActivity(), tasks, WEEKLY_TASK_FILE_NAME);
                    return true;
                });
                menu.getItem(2).setOnMenuItemClickListener(item -> {
                    Intent intent = new Intent(requireActivity(),
                            AddTaskActivity.class);
                    intent.putExtra("position_tab",1);
                    itemLauncher.launch(intent);
                    return true;
                });
            }
            public TextView getTaskName(){
                return task_name;
            }
            public TextView getTaskScore(){
                return tasks_score;
            }
            public TextView getTaskFinish(){return num;}
        }
        public WeeklyCustomAdapter(ArrayList<Task> dataSet) {
            task_item = dataSet;
        }

        @NonNull
        public WeeklyCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.tasks_daily_list, viewGroup, false);
            return new WeeklyCustomAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getTaskName().setText(task_item.get(position).getTaskName());
            holder.getTaskScore().setText(task_item.get(position).getScore());
            holder.getTaskFinish().setText(task_item.get(position).getNum());
            holder.checkBox.setTag(position);
            holder.checkBox.setChecked(false);
        }

        @Override
        public int getItemCount() {
            return task_item.size();
        }
    }
    static class TaskComparator implements Comparator<Task> {
        @Override
        public int compare(Task task1, Task task2) {
            // 按照score进行升序排序
            return Integer.compare((int) Double.parseDouble(task1.getScore().substring(1)),(int)Double.parseDouble(task2.getScore().substring(1)));
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public static void Sort_weekly_task(){
        tasks.sort(new TaskComparator());
        adapter.notifyDataSetChanged(); // 通知适配器数据发生了变化
    }
}