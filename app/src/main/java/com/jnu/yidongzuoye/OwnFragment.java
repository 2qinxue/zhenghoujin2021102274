package com.jnu.yidongzuoye;

import static android.app.Activity.RESULT_CANCELED;
import static com.jnu.yidongzuoye.MainActivity.isClosed;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.yidongzuoye.data.DataBankInformation;
import com.jnu.yidongzuoye.data.Ownitemgroup;
import com.jnu.yidongzuoye.data.personality_information;
import com.jnu.yidongzuoye.ownopera.ChangeavatarActivity;
import com.jnu.yidongzuoye.ownopera.LoginActivity;
import java.util.ArrayList;
import java.util.Date;

public class OwnFragment extends Fragment {

    private static boolean on_click = false, on_click2 = false, on_click3 = false;
    OwnCustomAdapter adapter;

    public static ActivityResultLauncher<Intent> imageLauncher;

    private final String[] item = {"我的钱包", "副本设置", "退出登录", "帮助", "检测更新", "请作者喝白开水"};
    ArrayList<Ownitemgroup> ownitemgroups = new ArrayList<>();
    public OwnFragment() {
        // Required empty public constructor
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
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
        ArrayList<personality_information> information= new ArrayList<>();
        try {
            information= new DataBankInformation().informationInput(requireActivity().getApplicationContext());
        }
        catch (Exception e) {
            information.add(new personality_information("张三", R.drawable.p));
        }
        adapter = new OwnCustomAdapter(ownitemgroups,information.get(0).getname(),information.get(0).getimageresource());
        recycle_view.setAdapter(adapter);


        imageLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int newimageresource = R.drawable.p;

                        if (data != null) {
                            newimageresource = data.getIntExtra("image_resource_id", R.drawable.p);
                            OwnCustomAdapter.imageresource=newimageresource;
                        }
                        OwnCustomAdapter.ViewHolder1.imageButton.setImageResource(newimageresource);
                        ArrayList<personality_information> infors = new ArrayList<>();
                        infors.add(new personality_information(OwnCustomAdapter.ViewHolder1.ownname.getText().toString(), newimageresource));
                        new DataBankInformation().saveinformations(requireActivity(), infors);
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

        public static String name;
        public static int imageresource;
        public OwnCustomAdapter(ArrayList<Ownitemgroup> dataSet,String name,int imageresource) {
            button_item = dataSet;
            // 绑定 item1.xml 中的视图和数据
            OwnCustomAdapter.name = name;
            OwnCustomAdapter.imageresource = imageresource;
        }


        public static class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            @SuppressLint("StaticFieldLeak")
            public static TextView ownname;
            @SuppressLint("StaticFieldLeak")
            public static ImageButton imageButton;


            public ViewHolder1(View own_item_view) {
                super(own_item_view);
                ownname = own_item_view.findViewById(R.id.textViewown);
                imageButton = own_item_view.findViewById(R.id.imageButton);
                imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                imageButton.setAdjustViewBounds(true);
                own_item_view.setOnCreateContextMenuListener(this);
                imageButton.setOnClickListener(v -> {
                    // 创建对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    LayoutInflater inflater = LayoutInflater.from(v.getContext());
                    View dialogView = inflater.inflate(R.layout.dialog_confirm_change, null);

                    builder.setView(dialogView)
                            .setTitle("更换头像")
                            .setMessage("是否确定更换头像？")
                            .setPositiveButton("确认", (dialog, which) -> {
                                // 在这里执行跳转到新页面的操作
                                Intent intent = new Intent(v.getContext(), ChangeavatarActivity.class);
                                imageLauncher.launch(intent);
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                });
                ownname.setOnClickListener(v -> {
                    // 创建对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    LayoutInflater inflater = LayoutInflater.from(v.getContext());
                    View dialogView = inflater.inflate(R.layout.dialog_change_name, null);
                    final EditText editTextName = dialogView.findViewById(R.id.editTextText2);
                    editTextName.setText(ownname.getText().toString());
                    builder.setView(dialogView)
                            .setTitle("更改名字")
                            .setPositiveButton("确认", (dialog, which) -> {
                                // 获取用户输入的新名字
//                                String newName = editTextName.getText().toString().trim();
                                name = editTextName.getText().toString();
                                // 设置对话框的文本为新名字
                                ownname.setText(name);
                                ArrayList<personality_information> infors = new ArrayList<>();
                                infors.add(new personality_information(name,imageresource));
                                new DataBankInformation().saveinformations(v.getContext(),infors);
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();

                });

            }
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            }
        }

        public static class ViewHolder2 extends RecyclerView.ViewHolder {
            // ViewHolder2 的代码...
            private final Button button;
            public ViewHolder2(View own_item_view) {
                super(own_item_view);
                button = own_item_view.findViewById(R.id.button);
            }
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

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Ownitemgroup buttongroup = button_item.get(position);

            switch (holder.getItemViewType()) {
                case VIEW_TYPE_1:
                    String n=name;
                    int i=imageresource;
                    ViewHolder1.ownname.setText(n);
                    ViewHolder1.imageButton.setImageResource(i);
//                     设置图片适应 ImageButton 的大小
                    ViewHolder1.imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ViewHolder1.imageButton.setAdjustViewBounds(true);
                    // 其他绑定逻辑...
                    break;
                case VIEW_TYPE_2:
                    ViewHolder2 viewHolder2 = (ViewHolder2) holder;

                    // 将 Button 的文本右对齐
                    viewHolder2.button.setGravity(Gravity.START);
                    viewHolder2.button.setBackgroundColor(Color.TRANSPARENT);
                    viewHolder2.button.setText(buttongroup.getButtonname());
                    viewHolder2.button.setOnClickListener(v -> {
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
                                button.setOnClickListener(v1 -> {
                                    if(Double.parseDouble(textView.getText().toString().substring(1))==0)
                                        Toast.makeText(v1.getContext(), "钱包没钱了！！", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(v1.getContext(), "提现成功", Toast.LENGTH_SHORT).show();
                                    textView.setText("￥0.00");
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
                                LayoutInflater inflater_2 = LayoutInflater.from(v.getContext());
                                View dialogView_2 = inflater_2.inflate(R.layout.instance_setting, null);
                                CheckBox checkBox = dialogView_2.findViewById(R.id.checkBox2);
                                checkBox.setChecked(on_click);
                                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> on_click = isChecked);
                                CheckBox checkBox2 = dialogView_2.findViewById(R.id.checkBox3);
                                checkBox2.setChecked(on_click2);
                                checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> on_click2 = isChecked);
                                CheckBox checkBox3 = dialogView_2.findViewById(R.id.checkBox4);
                                checkBox3.setChecked(on_click3);
                                checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> on_click3 = isChecked);
                                builder1.setView(dialogView_2);
                                builder1.setTitle("副本设置");
                                builder1.setNegativeButton("关闭", null);
                                builder1.create();
                                builder1.show();
                                break;
                            case 3:
                                Intent intent = new Intent();
                                intent.setClass(v.getContext(), LoginActivity.class);
                                intent.putExtra("img_id", imageresource);
                                intent.putExtra("ac_name", name);
                                v.getContext().startActivity(intent);
                                isClosed=true;
                                break;
                            case 4:
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(v.getContext());
                                builder2.setTitle("帮助");
                                builder2.setMessage("这么简单的操作还需要帮助？");
                                builder2.setPositiveButton("帮助", (dialog, which) -> Toast.makeText(v.getContext(), "没有帮助", Toast.LENGTH_SHORT).show());
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
                    });
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + holder.getItemViewType());
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

