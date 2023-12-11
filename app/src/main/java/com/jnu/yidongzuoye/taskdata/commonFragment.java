package com.jnu.yidongzuoye.taskdata;

import static android.app.Activity.RESULT_CANCELED;

import static com.jnu.yidongzuoye.MainActivity.allBills;
import static com.jnu.yidongzuoye.data.DataBankTask.COMMON_TASK_FILE_NAME;

import static com.jnu.yidongzuoye.data.DataBankTask.COMMON_TASK_STORE_FILENAME;
import static com.jnu.yidongzuoye.data.DataBankTask.DAILY_TASK_DATA_FILE_NAME;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.yidongzuoye.R;
import com.jnu.yidongzuoye.data.Bill;
import com.jnu.yidongzuoye.data.DataBankBill;
import com.jnu.yidongzuoye.data.DataBankTask;
import com.jnu.yidongzuoye.data.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;


public class commonFragment extends Fragment {

    private int parentPosition;
    private int childPosition;
    private static ArrayList<Task> tasks = new ArrayList<>();
    public static ActivityResultLauncher<Intent> itemLauncher;

    private ArrayList<Task> tasks_store = new ArrayList<>();
    private static CommonCustomAdapter adapter;
    public commonFragment() {
        // Required empty public constructor
    }

    public static commonFragment newInstance(int parentPosition, int childPosition) {
        commonFragment fragment = new commonFragment();
        fragment.parentPosition = parentPosition;
        fragment.childPosition = childPosition;
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_taskdaily, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycle_view_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        try{
            tasks_store.add(new Task("外出散步30分钟", "+30", "0/30"));
            tasks_store.add(new Task("做家务30分钟）", "+100", "0/100"));
            tasks_store.add(new Task("学习一次", "+120", "0/120"));
            tasks_store.add(new Task("眼保健操一次", "+2", "0/1"));
            tasks_store.add(new Task("复习30分钟", "+30", "0/30"));
            tasks_store.add(new Task("完成作业", "+5", "0/1"));
            tasks_store.add(new Task("做10次俯卧撑", "+10", "0/10"));
            tasks_store.add(new Task("英语阅读30分钟", "+30", "0/30"));
            new DataBankTask().saveTasks(requireActivity().getApplicationContext(), tasks_store,COMMON_TASK_STORE_FILENAME);
            tasks=new DataBankTask().tasksInput(requireActivity(),COMMON_TASK_FILE_NAME);
        }
        catch (Exception e){
            tasks.add(new Task("外出散步30分钟", "+30", "0/30"));
            new DataBankTask().saveTasks(requireActivity(), tasks,COMMON_TASK_FILE_NAME);
        }

        if( tasks.size()==0){
            tasks.add(new Task("外出散步30分钟", "+30", "0/30"));
        }
        adapter = new CommonCustomAdapter(tasks);
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        itemLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String name = data.getStringExtra("name");
                        Double score_;
                        String score_text = data.getStringExtra("score");
                        try {
                            score_ = Double.parseDouble(score_text);// 输入的数据是 double 类型
                        } catch (NumberFormatException e) {
                            score_ = 0.0;   // 输入的数据不是 double 类型
                        }
                        String finish_text = data.getStringExtra("finish_num");
                        tasks.add(new Task(name, "+" + score_,finish_text));
                        adapter.notifyItemInserted(tasks.size());
                        new DataBankTask().saveTasks(requireActivity(), tasks, COMMON_TASK_FILE_NAME);
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        Toast.makeText(requireActivity(), "哈哈哈", Toast.LENGTH_SHORT).show();
                    }
                });
        
        return rootView;
    }
    public class CommonCustomAdapter extends  RecyclerView.Adapter<CommonCustomAdapter.ViewHolder>{
        private ArrayList<Task> task_item;

        public void removeItem(int position) {
            task_item.remove(position);
            notifyItemRemoved(position);
        }//删除books
        public class ViewHolder extends RecyclerView.ViewHolder
                implements View.OnCreateContextMenuListener {
            private CheckBox checkBox;
            private TextView task_name;
            private  TextView tasks_score;
            private TextView num;
            public ViewHolder(View task_item_view) {
                super(task_item_view);

                this.task_name = task_item_view.findViewById(R.id.textView_daily_task_name);
                this.tasks_score = task_item_view.findViewById(R.id.textView_daily_score);
                this.num = task_item_view.findViewById(R.id.textView_daily_finish);
                checkBox = task_item_view.findViewById(R.id.checkBox);
                checkBox.setChecked(false);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                allBills = new DataBankBill().billsInput(requireActivity());
                            }
                            catch (Exception e){
                                allBills = new ArrayList<>();
                            }
                            Date now = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                    }
                });
                task_item_view.setOnCreateContextMenuListener(this);
            }
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
                menu.setHeaderTitle("具体操作");
                menu.add(0,0,this.getAdapterPosition(),"删除"+this.getAdapterPosition());
                menu.add(0,1,this.getAdapterPosition(),"完成"+this.getAdapterPosition());
                menu.add(0,2,this.getAdapterPosition(),"新建"+this.getAdapterPosition());
                menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                        builder.setTitle("删除");
                        builder.setMessage("确定删除？");
                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.removeItem(item.getOrder());
                                new DataBankTask().saveTasks(requireActivity(), tasks, COMMON_TASK_FILE_NAME);
                                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                        return true;
                    }
                });
                menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Date now = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                        new DataBankTask().saveTasks(requireActivity(), tasks, COMMON_TASK_FILE_NAME);
                        return true;
                    }
                });
                menu.getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(requireActivity(),
                                AddTaskActivity.class);
                        intent.putExtra("position_tab",2);
                        itemLauncher.launch(intent);
                        return true;
                    }
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
        public CommonCustomAdapter(ArrayList<Task> dataSet) {
            task_item = dataSet;
        }

        public CommonCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.tasks_daily_list, viewGroup, false);
            return new CommonCustomAdapter.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull CommonCustomAdapter.ViewHolder holder, int position) {
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
    public static void Sort_common_task(){
        Collections.sort(tasks, new commonFragment.TaskComparator());
        adapter.notifyDataSetChanged(); // 通知适配器数据发生了变化
    }
}