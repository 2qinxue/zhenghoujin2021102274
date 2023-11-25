package com.jnu.myrecycle.taskdata;

import static com.jnu.myrecycle.data.DataBankTask.FINISH_TASK_DATA_FILE_NAME;
import static com.jnu.myrecycle.data.DataBankTask.WEEKLY_TASK_FILE_NAME;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.myrecycle.R;
import com.jnu.myrecycle.data.DataBankTask;
import com.jnu.myrecycle.data.Task;

import java.util.ArrayList;

public class weeklyFragment extends Fragment {

    private int parentPosition;
    private int childPosition;
    private ArrayList<Task> tasks = new ArrayList<>();
    private WeeklyCustomAdapter adapter;
    public weeklyFragment() {
        // Required empty public constructor
    }
    public static weeklyFragment newInstance(int parentPosition, int childPosition) {
        weeklyFragment fragment = new weeklyFragment();
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
       // View rootView = inflater.inflate(R.layout.fragment_weekly, container, false);
        View rootView = inflater.inflate(R.layout.fragment_taskdaily, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycle_view_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        tasks.add(new Task("普通阅读100分钟", "+150", "0/100"));
        tasks.add(new Task("专业阅读200分钟）", "+200", "0/200"));
        tasks.add(new Task("Keep 课程5次", "+200", "0/5"));
        tasks.add(new Task("深蹲10套 ", "+20", "0/10"));
        tasks.add(new Task("学习视频200分钟", "+100", "0/200"));
        tasks.add(new Task("早睡5次", "+50", "0/5"));
        tasks.add(new Task("背100个单词", "+30", "0/100"));
        tasks.add(new Task("跑步300分钟", "+100", "0/300"));
        adapter = new WeeklyCustomAdapter(tasks);
        recyclerView.setAdapter(adapter);

        return rootView;
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
                        new DataBankTask().saveTasks(requireActivity(), tasks,WEEKLY_TASK_FILE_NAME);
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
                new DataBankTask().saveTasks(requireActivity(), tasks,FINISH_TASK_DATA_FILE_NAME);
                adapter.removeItem(item.getOrder());
                new DataBankTask().saveTasks(requireActivity(), tasks,WEEKLY_TASK_FILE_NAME);
                Toast.makeText(getContext(), "已完成", Toast.LENGTH_SHORT).show();
            }


            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    public class WeeklyCustomAdapter extends  RecyclerView.Adapter<WeeklyCustomAdapter.ViewHolder>{
        private ArrayList<Task> task_item;

        public void removeItem(int position) {
            task_item.remove(position);
            notifyItemRemoved(position);
        }//删除books

        public void editItem(int position, String newName, String newScore) {
            Task task = task_item.get(position);
            task.setTaskName(newName);
            task.setScore(newScore);
            notifyItemChanged(position);
        }
        public class ViewHolder extends RecyclerView.ViewHolder
                implements View.OnCreateContextMenuListener {
            private TextView task_name;
            private  TextView tasks_score;
            private TextView num;
            public ViewHolder(View task_item_view) {
                super(task_item_view);

                this.task_name = task_item_view.findViewById(R.id.textView_daily_task_name);
                this.tasks_score = task_item_view.findViewById(R.id.textView_daily_score);
                this.num = task_item_view.findViewById(R.id.textView_daily_finish);
                task_item_view.setOnCreateContextMenuListener(this);
            }
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
                menu.setHeaderTitle("具体操作");
                menu.add(0,0,this.getAdapterPosition(),"删除"+this.getAdapterPosition());
                menu.add(0,1,this.getAdapterPosition(),"完成"+this.getAdapterPosition());
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
        }


        @Override
        public int getItemCount() {
            return task_item.size();
        }
    }
}