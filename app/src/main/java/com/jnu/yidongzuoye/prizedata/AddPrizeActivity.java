package com.jnu.yidongzuoye.prizedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.yidongzuoye.R;
import com.jnu.yidongzuoye.data.DataBankPrize;
import com.jnu.yidongzuoye.data.Prize;

import java.util.ArrayList;

public class AddPrizeActivity extends AppCompatActivity {
    EditText name, score,textView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_prize);
            name= findViewById(R.id.edittextView_daily_prize_name1);
            score = findViewById(R.id.edittextView_daily_score1);
            RecyclerView recyclerView = findViewById(R.id.recycle1);
            textView= findViewById(R.id.textView_daily_finish1);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            Button button_OK = findViewById(R.id.button_OK1);
            button_OK.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("name",   name.getText().toString());
                    intent.putExtra("score", score.getText().toString());
                    intent.putExtra("finish_num", textView.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
            ArrayList<Prize> prizes = new DataBankPrize().prizesInput(this, DataBankPrize.PRIZE_STORE_DATA_FILE_NAME);
            CustomAdapter adapter = new CustomAdapter(prizes);
            recyclerView.setAdapter(adapter);
        }

            class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.PrizeAddCustomViewHolder> {
                private final ArrayList<Prize> prizes_item;

                public CustomAdapter(ArrayList<Prize> prize) {
                    this.prizes_item = prize;
                }

                @NonNull
                @Override
                public PrizeAddCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prize_detail_list, parent, false);
                    return new PrizeAddCustomViewHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull PrizeAddCustomViewHolder holder, int position) {
                    holder.getItemName().setText(prizes_item.get(position).getPrizeName());
                    holder.getItemScore().setText(prizes_item.get(position).getScore());
                    holder.getItemFinish().setText(prizes_item.get(position).getNum());
                }
                @Override
                public int getItemCount() {
                    return prizes_item.size();
                }



            class PrizeAddCustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                private TextView itemName, item_score, item_finsih;

                ImageButton imageButton;
                public PrizeAddCustomViewHolder(@NonNull View itemView) {
                    super(itemView);
                    itemName = itemView.findViewById(R.id.textView_daily_prize_name);
                    item_score = itemView.findViewById(R.id.textView_prize_score);
                    item_finsih = itemView.findViewById(R.id.textView_prize_finish);
                    imageButton = itemView.findViewById(R.id.imageButton_label);
                    imageButton.setVisibility(View.GONE);
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
                    Prize prizes_ = prizes_item.get(position);
                    // 显示Book对象的内容，可以使用Toast或者其他方式来显示
                    Toast.makeText(v.getContext(), "Clicked on " + prizes_.getPrizeName(), Toast.LENGTH_SHORT).show();
                    name.setText(prizes_.getPrizeName());
                    score.setText(prizes_.getScore());
                    textView.setText(prizes_.getNum());
                }
            }
        }
}