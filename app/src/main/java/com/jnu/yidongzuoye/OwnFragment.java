package com.jnu.yidongzuoye;

import static android.app.Activity.RESULT_CANCELED;
import static com.jnu.yidongzuoye.data.DataBankTask.DAILY_TASK_DATA_FILE_NAME;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.yidongzuoye.data.DataBankTask;
import com.jnu.yidongzuoye.data.Ownitemgroup;
import com.jnu.yidongzuoye.data.Task;
import com.jnu.yidongzuoye.ownopera.ChangeavatarActivity;
import com.jnu.yidongzuoye.ownopera.LoginActivity;
import com.jnu.yidongzuoye.view.IncomBIll;

import java.util.ArrayList;
import java.util.Date;

public class OwnFragment extends Fragment {

    OwnCustomAdapter adapter;

    public static ActivityResultLauncher<Intent> imageLauncher;

    private final String[] item = {"我的钱包", "副本设置", "退出登录", "帮助", "检测更新", "请作者喝白开水"};
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

        imageLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int imageresource = data.getIntExtra("image_resource_id", R.drawable.p);
                        OwnCustomAdapter.ViewHolder1.imageButton.setImageResource(imageresource);
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        Toast.makeText(requireActivity(), "哈哈哈", Toast.LENGTH_SHORT).show();
                    }
                });
        return rootview;
    }

    public static class OwnCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final ArrayList<Ownitemgroup> button_item;
        public static final int VIEW_TYPE_1 = 1;
        public static final int VIEW_TYPE_2 = 2;
        public static class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView ownname;
            public static ImageButton imageButton = null;

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
                                        imageLauncher.launch(intent);
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
            private final Button button;
            public ViewHolder2(View own_item_view) {
                super(own_item_view);
                button = own_item_view.findViewById(R.id.button);
            }
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
//                            Ownitemgroup buttongroup = (Ownitemgroup) v.getTag();
                            switch(viewHolder2.getPosition())
                            {
                                case 1:
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    LayoutInflater inflater = LayoutInflater.from(v.getContext());
                                    View dialogView = inflater.inflate(R.layout.mywallet, null);
                                    TextView textView = dialogView.findViewById(R.id.textView_yue);
                                    textView.setText("￥" + new Date().getHours());
                                    Button button = dialogView.findViewById(R.id.button_pay);
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(Double.parseDouble(textView.getText().toString().substring(1))==0)
                                                Toast.makeText(v.getContext(), "钱包没钱了！！", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(v.getContext(), "提现成功", Toast.LENGTH_SHORT).show();
                                            textView.setText("￥0.00");
                                        }
                                    });
                                    ImageView moneyImageView = dialogView.findViewById(R.id.moneyImageView);
                                    ObjectAnimator moneyAnimator = ObjectAnimator.ofFloat(moneyImageView, "translationY", 0f, 1000f);
                                    moneyAnimator.setDuration(4000); // 设置动画持续时间为2秒
                                    moneyAnimator.setRepeatCount(ValueAnimator.INFINITE); // 设置重复次数为无限次
                                    moneyAnimator.addListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            moneyImageView.setVisibility(View.INVISIBLE); // 动画结束后将 ImageView 设置为不可见
                                        }
                                    });
                                    moneyImageView.setVisibility(View.VISIBLE); // 将 ImageView 设置为可见
                                    moneyAnimator.start();
                                    builder.setView(dialogView);
                                    builder.setNegativeButton("关闭", null);
                                    builder.create();
                                    builder.show();
                                    break;
                                }
                                case 2:

                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                                    builder1.setTitle("副本设置");
                                    builder1.setNegativeButton("关闭", null);
                                    builder1.create();
                                    builder1.show();

                                    break;
                                case 3:
                                    Intent intent = new Intent();
                                    intent.setClass(v.getContext(), LoginActivity.class);
                                    v.getContext().startActivity(intent);
                                    break;
                                case 4:
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(v.getContext());
                                    builder2.setTitle("帮助");
                                    builder2.setMessage("这么简单的操作还需要帮助？");
                                    builder2.setPositiveButton("帮助", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(v.getContext(), "没有帮助", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    builder2.setNegativeButton("关闭", null);
                                    builder2.create();
                                    builder2.show();
                                    break;
                                case 5:
                                    AlertDialog.Builder builder3 = new AlertDialog.Builder(v.getContext());
                                    builder3.setTitle("检测更新");
                                    builder3.setMessage("检测到为：绝版");
                                    builder3.setNegativeButton("关闭", null);
                                    builder3.create();
                                    builder3.show();
                                    break;
                                case 6:
                                    AlertDialog.Builder builder4 = new AlertDialog.Builder(v.getContext());
                                    LayoutInflater inflater = LayoutInflater.from(v.getContext());
                                    View dialogView = inflater.inflate(R.layout.water, null);
                                    ImageView waterImageView =dialogView.findViewById(R.id.imageView_water);
                                    waterImageView.setBackgroundResource(R.drawable.water_flow_animation);
                                    AnimationDrawable waterAnimation = (AnimationDrawable) waterImageView.getBackground();
                                    waterAnimation.start();
                                    builder4.setView(dialogView);
                                    builder4.setNegativeButton("关闭", null);
                                    builder4.create();
                                    builder4.show();
                                    break;

                            }
                        }
                    });
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

