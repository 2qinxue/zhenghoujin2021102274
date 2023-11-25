package com.jnu.myrecycle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jnu.myrecycle.data.Ownitemgroup;
import com.jnu.myrecycle.ownopera.ChangeavatarActivity;
import java.util.ArrayList;

public class OwnFragment extends Fragment {

    OwnCustomAdapter adapter;

    private int button_position;
    private String item[] = {"我的钱包", "副本设置", "退出登录", "帮助", "检测更新", "请作者喝除草剂"};
    ArrayList<Ownitemgroup> ownitemgroups = new ArrayList<>();

    public OwnFragment() {
        // Required empty public constructor
    }

    public static OwnFragment newInstance(String param1, String param2) {
        OwnFragment fragment = new OwnFragment();
        Bundle args = new Bundle();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // 清除菜单项
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_own_list, container, false);
        RecyclerView recycle_view = rootview.findViewById(R.id.recycle_view_own);
        recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        ownitemgroups.add(new Ownitemgroup("张三",0));

        for (int i = 0; i < item.length; i++) {
            ownitemgroups.add(new Ownitemgroup(item[i],i+1));
        }

        adapter = new OwnCustomAdapter(ownitemgroups);
        recycle_view.setAdapter(adapter);
        return rootview;
    }

    public class OwnCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<Ownitemgroup> button_item;
        public static final int VIEW_TYPE_1 = 1;
        public static final int VIEW_TYPE_2 = 2;
        public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private TextView ownname;
            private ImageButton imageButton;

            public ViewHolder1(View own_item_view) {
                super(own_item_view);
                this.ownname = own_item_view.findViewById(R.id.textViewown);
                this.imageButton = own_item_view.findViewById(R.id.imageButton);
                own_item_view.setOnCreateContextMenuListener(this);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 创建对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        LayoutInflater inflater = LayoutInflater.from(v.getContext());
                        View dialogView = inflater.inflate(R.layout.dialog_confirm_change, null);

                        builder.setView(dialogView)
                                .setTitle("更换头像")
                                .setMessage("是否确定更换头像？")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 在这里执行跳转到新页面的操作
                                        Intent intent = new Intent(v.getContext(), ChangeavatarActivity.class);
                                        v.getContext().startActivity(intent);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .create()
                                .show();
                    }
                });
                ownname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 创建对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        LayoutInflater inflater = LayoutInflater.from(v.getContext());
                        View dialogView = inflater.inflate(R.layout.dialog_change_name, null);
                        final EditText editTextName = dialogView.findViewById(R.id.editTextText2);
                        editTextName.setText(ownname.getText().toString());
                        builder.setView(dialogView)
                                .setTitle("更改名字")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 获取用户输入的新名字
                                        String newName = editTextName.getText().toString().trim();
                                        // 设置对话框的文本为新名字
                                        ownname.setText(newName);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .create()
                                .show();
                    }
                });

            }
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            }
        }

        public class ViewHolder2 extends RecyclerView.ViewHolder {
            // ViewHolder2 的代码...
            private Button button;

            public ViewHolder2(View own_item_view) {
                super(own_item_view);
                button = own_item_view.findViewById(R.id.button);
            }

            // ViewHolder2 的其他方法...
        }

        public OwnCustomAdapter(ArrayList<Ownitemgroup> dataSet) {
            button_item = dataSet;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView;
            switch (viewType) {
                case VIEW_TYPE_1:
                    itemView = inflater.inflate(R.layout.own_detail_list, parent, false);
                    return new ViewHolder1(itemView);
                case VIEW_TYPE_2:
                    itemView = inflater.inflate(R.layout.own_button_list, parent, false);
                    return new ViewHolder2(itemView);
                default:
                    throw new IllegalArgumentException("Invalid view type: " + viewType);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Ownitemgroup buttongroup = button_item.get(position);
            switch (holder.getItemViewType()) {
                case VIEW_TYPE_1:
                    ViewHolder1 viewHolder1 = (ViewHolder1) holder;
                    // 绑定 item1.xml 中的视图和数据
                    viewHolder1.ownname.setText("张三");
                    viewHolder1.imageButton.setImageResource(R.drawable.p);

                    // 设置图片适应 ImageButton 的大小
                    viewHolder1.imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    viewHolder1.imageButton.setAdjustViewBounds(true);
                    // 其他绑定逻辑...
                    break;
                case VIEW_TYPE_2:
                    ViewHolder2 viewHolder2 = (ViewHolder2) holder;

                    // 将 Button 的文本右对齐
                    viewHolder2.button.setGravity(Gravity.START);
                    viewHolder2.button.setBackgroundColor(Color.TRANSPARENT);
                    viewHolder2.button.setText(buttongroup.getButtonname());
                    viewHolder2.button.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Ownitemgroup buttongroup = (Ownitemgroup) v.getTag();
                            switch(viewHolder2.getPosition())
                            {
                                case 1:
                                {
                                    Toast.makeText(v.getContext(), "11", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                case 2:
                                    Toast.makeText(v.getContext(), "22", Toast.LENGTH_SHORT).show();
                                    break;
                                case 3:
                                    Toast.makeText(v.getContext(), "33", Toast.LENGTH_SHORT).show();
                                    break;
                                case 4:
                                    Toast.makeText(v.getContext(), "44", Toast.LENGTH_SHORT).show();
                                    break;
                                case 5:
                                    Toast.makeText(v.getContext(), "55", Toast.LENGTH_SHORT).show();
                                    break;
                                case 6:
                                    Toast.makeText(v.getContext(), "66", Toast.LENGTH_SHORT).show();
                                    break;

                            }
                        }
                    });
                    // 绑定 item2.xml 中的视图和数据

                    // 其他绑定逻辑...
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            // 根据位置或数据源的条件返回相应的布局类型
            return position < 1 ? VIEW_TYPE_1 : VIEW_TYPE_2;
        }

        @Override
        public int getItemCount() {
            return button_item.size();
        }
    }
}

