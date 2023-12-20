package com.jnu.yidongzuoye.taskdata;

import static com.jnu.yidongzuoye.data.DataBankTask.COMMON_TASK_STORE_FILENAME;
import static com.jnu.yidongzuoye.data.DataBankTask.DAILY_TASK_STORE_FILENAME;
import static com.jnu.yidongzuoye.data.DataBankTask.INSTANCE_TASK_STORE_filename;
import static com.jnu.yidongzuoye.data.DataBankTask.WEEKLY_TASK_STORE_FILENAME;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.yidongzuoye.R;
import com.jnu.yidongzuoye.data.DataBankTask;
import com.jnu.yidongzuoye.data.Task;

import java.util.ArrayList;
public class AddTaskActivity extends AppCompatActivity {
    private final String[] name_tab ={DAILY_TASK_STORE_FILENAME,WEEKLY_TASK_STORE_FILENAME,COMMON_TASK_STORE_FILENAME,INSTANCE_TASK_STORE_filename};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        EditText name = findViewById(R.id.edittextView_daily_task_name);
        EditText score = findViewById(R.id.edittextView_daily_score);
        RecyclerView recyclerView = findViewById(R.id.recycle);
        EditText textView = findViewById(R.id.edittext_daily_finish);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int position_tab = getIntent().getIntExtra("position_tab", 0);
        Button button_OK = findViewById(R.id.button_OK);
        button_OK.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("name", "" + name.getText().toString() + "");
            intent.putExtra("score", "" + score.getText().toString());
            intent.putExtra("finish_num", textView.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        ArrayList<Task> tasks = new DataBankTask().tasksInput(this, name_tab[position_tab]);
        class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
            private final ArrayList<Task> tasks_item;

            public CustomAdapter(ArrayList<Task> tasks) {
                this.tasks_item = tasks;
            }

            @NonNull
            @Override
            public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_daily_list, parent, false);
                return new CustomViewHolder(view);
            }
            @Override
            public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
                holder.getItemName().setText(tasks_item.get(position).getTaskName());
                holder.getItemScore().setText(tasks_item.get(position).getScore());
                holder.getItemFinish().setText(tasks_item.get(position).getNum());
            }

            @Override
            public int getItemCount() {
                return tasks_item.size();
            }

            class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                private final TextView itemName;
                private final TextView item_score;
                private final TextView item_finsih;

                public CustomViewHolder(@NonNull View itemView) {
                    super(itemView);
                    itemName = itemView.findViewById(R.id.textView_daily_task_name);
                    item_score = itemView.findViewById(R.id.textView_daily_score);
                    item_finsih = itemView.findViewById(R.id.textView_daily_finish);
                    itemView.setOnClickListener(this);
                }
                public TextView getItemName() {
                    return itemName;
                }

                public TextView getItemScore() {
                    return item_score;
                }

                public TextView getItemFinish() {
                    return item_finsih;
                }

                @Override
                public void onClick(View v) {
                    // 获取被点击项的位置
                    int position = getAdapterPosition();
                    // 根据位置获取对应的Book对象
                    Task taskk = tasks_item.get(position);
                    // 显示Book对象的内容，可以使用Toast或者其他方式来显示
                    Toast.makeText(v.getContext(), "Clicked on " + taskk.getTaskName(), Toast.LENGTH_SHORT).show();
                    name.setText(taskk.getTaskName());
                    score.setText(taskk.getScore());
                    textView.setText(taskk.getNum());
                }
            }
        }
        CustomAdapter adapter = new CustomAdapter(tasks);
        recyclerView.setAdapter(adapter);
    }
}