//package com.jnu.myrecycle;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.ContextMenu;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.jnu.myrecycle.data.Book;
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//
//    private ActivityResultLauncher<Intent>itemLauncher;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//
//        RecyclerView recyclerView = findViewById(R.id.recycle_view_books);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        ArrayList<Book> books =new ArrayList<>();
//        books.add(new Book("信息安全教学基础（第2版）", 100, R.drawable.book_1));
//        books.add(new Book("软件管理项目案例教程（第4版）", 120, R.drawable.book_2));
//        books.add(new Book("创新工程实践", 30, R.drawable.book_no_name));
//        books.add(new Book("油画", 1024, R.drawable.a_oil_painting));
//
//        CustomAdapter adapter = new CustomAdapter(books);
//        recyclerView.setAdapter(adapter);
//
//        registerForContextMenu(recyclerView);
//
//        itemLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        String name = data.getStringExtra("name");
//                        String price_text = data.getStringExtra("price");
//                        Double price=Double.parseDouble(price_text);
//                        books.add(new Book(name, price, R.drawable.book_no_name));
//                        adapter.notifyItemInserted(books.size());
//                    }
//                    else if (result.getResultCode() == RESULT_CANCELED) {
//                        Toast.makeText(MainActivity.this, "哈哈哈哈哈哈哈哈哈", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//    private String getAdapterPosition() {
//        return "position:" + this.getAdapterPosition();
//    }
//
//
//    public boolean onContextItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case 0:
//                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
//                break;
//            case 1:
//                Intent intent = new Intent(MainActivity.this,
//                        shop_item_MainActivity.class);
//                itemLauncher.launch(intent);
//                break;
//            case 2:
//                Toast.makeText(MainActivity.this, "商品"+item.getOrder()
//                        +"修改", Toast.LENGTH_SHORT).show();
//                break;
//            case 3:
//                Toast.makeText(MainActivity.this, "商品"+item.getOrder()
//                        +"\n加入购物车", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                return super.onContextItemSelected(item);
//        }
//        return true;
//    }
//    public class CustomAdapter extends  RecyclerView.Adapter<CustomAdapter.ViewHolder>{
//        private ArrayList<Book> BOOK_Shop_item;
//        public class ViewHolder extends RecyclerView.ViewHolder
//                implements View.OnCreateContextMenuListener {
//            private final TextView item_name;
//            private final TextView item_price;
//            private final ImageView item_image;
//            public ViewHolder(View shop_item_view) {
//                super(shop_item_view);
//                this.item_name = shop_item_view.findViewById(R.id.text_view_book_title);
//                this.item_price = shop_item_view.findViewById(R.id.item_price);
//                this.item_image = shop_item_view.findViewById(R.id.image_view_book_cover);
//                shop_item_view.setOnCreateContextMenuListener(this);
//            }
//            public void onCreateContextMenu(ContextMenu menu, View v,
//                                            ContextMenu.ContextMenuInfo menuInfo){
//                menu.setHeaderTitle("具体操作");
//                menu.add(0,0,this.getAdapterPosition(),"删除"+this.getAdapterPosition());
//                menu.add(0,1,this.getAdapterPosition(),"添加"+this.getAdapterPosition());
//                menu.add(0,2,this.getAdapterPosition(),"修改"+this.getAdapterPosition());
//                menu.add(0,3,this.getAdapterPosition(),"【购买】"+this.getAdapterPosition());
//            }
//            public TextView getItemName(){
//                return item_name;
//            }
//            public TextView getItemPrice(){
//                return item_price;
//            }
//            public ImageView getItemImage(){return item_image;}
//        }
//        public CustomAdapter(ArrayList<Book> dataSet) {
//            BOOK_Shop_item = dataSet;
//        }
//
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = LayoutInflater.from(viewGroup.getContext()).
//                    inflate(R.layout.shop_item_row, viewGroup, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
//            holder.getItemName().setText(BOOK_Shop_item.get(position).getName());
//            holder.getItemPrice().setText(BOOK_Shop_item.get(position).getPrice()+"元");
//            holder.getItemImage().setImageResource(BOOK_Shop_item.get(position).getImageId());
//        }
//
//        @Override
//        public int getItemCount() {
//            return BOOK_Shop_item.size();
//        }
//    }
//
//}

package com.jnu.myrecycle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.myrecycle.data.Book;
import com.jnu.myrecycle.data.DataBank;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
//    private ActivityResultLauncher<Intent> itemLauncher;
//    private ActivityResultLauncher<Intent> imageLauncher_new;
//
//    private ArrayList<Book> books = new ArrayList<>();
//    private CustomAdapter adapter;
//    int position_id = 0;
    public String []tabName={"图书","新闻","地图","视频"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ViewPager2 viewPager2 = findViewById(R.id.viewPaGet2);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                tab.setText(tabName[(position)])).attach();
    }

    public class FragmentAdapter extends FragmentStateAdapter {
        private final int size_tab = tabName.length;
        public FragmentAdapter(FragmentManager supportFragmentManager, Lifecycle lifecycle) {
            super(supportFragmentManager,lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                    return new BookFragment();
                case 1:
                    Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                    return new WebFragment();
                case 2:
                    Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                    return new BaiduMapFragment();
                case 3:
                    Toast.makeText(MainActivity.this, "4", Toast.LENGTH_SHORT).show();
                    return new VedioFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return size_tab;
        }
    }
}

//        RecyclerView recyclerView = findViewById(R.id.recycle_main);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        Button button1 = findViewById(R.id.Button_Exit);
//        View.OnClickListener buttonClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v == button1) {
//                    finish();
//                } else
//                {
//                    Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
//                    itemLauncher.launch(intent);
//                }
//            }
//        };
//
//        button1.setOnClickListener(buttonClickListener);
//        try {
//            books = new DataBank().booksInput(this);
//        } catch (Exception e) {
//            books.add(new Book("创新工程实践", 30, R.drawable.book_no_name));
//            new DataBank().saveBooks(this.getApplicationContext(),books);
//        }
//        if(books.size()==0) {
////            books.add(new Book("信息安全教学基础（第2版）", 100, R.drawable.book_1));
////            books.add(new Book("软件管理项目案例教程（第4版）", 120, R.drawable.book_2));
//            books.add(new Book("创新工程实践", 30, R.drawable.book_no_name));
////            books.add(new Book("油画", 1024, R.drawable.a_oil_painting));
//        }  //添加书本
//        adapter = new CustomAdapter(books);
//        recyclerView.setAdapter(adapter);
//        registerForContextMenu(recyclerView);
//        itemLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        assert data != null;
//                        String name = data.getStringExtra("name");
//                        Double price;
//                        String price_text = data.getStringExtra("price");
//                        try {
//                            price = Double.parseDouble(price_text);
//                            // 输入的数据是 double 类型
//                        } catch (NumberFormatException e) {
//                            price=0.0;   // 输入的数据不是 double 类型
//                        }
//                        int position = data.getIntExtra("image_id", 0);
//                        books.add(new Book(name, price,R.drawable.book_no_name));
//                        adapter.notifyItemInserted(books.size());
//                        new DataBank().saveBooks(this.getApplicationContext(),books);
//                    }
//                    else if (result.getResultCode() == RESULT_CANCELED) {
//                        Toast.makeText(MainActivity.this, "哈哈哈哈哈哈哈哈哈", Toast.LENGTH_SHORT).show();
//                    }
//                });  //添加界面
//        imageLauncher_new = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        String new_name = data.getStringExtra("new_name");
//                        Double new_price;
//                        String price_text = data.getStringExtra("new_price");
//                        try {
//                            new_price = Double.parseDouble(price_text);
//                            // 输入的数据是 double 类型
//                        } catch (NumberFormatException e) {
//                            new_price = 0.0;   // 输入的数据不是 double 类型
//                        }
//                        adapter.editItem(position_id, new_name, new_price);
//                        new DataBank().saveBooks(this.getApplicationContext(),books);
//                    }
//                    else if (result.getResultCode() == RESULT_CANCELED) {
//                        Toast.makeText(MainActivity.this, "哈哈", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    public boolean onContextItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case 0:
//                AlertDialog.Builder builder =new AlertDialog.Builder(this);
//                builder.setTitle("删除");
//                builder.setMessage("确定删除？");
//                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                int position = item.getOrder();
//                                adapter.removeItem(position);
//                                new DataBank().saveBooks(MainActivity.this.getApplicationContext(),books);
//                                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                builder.show();
//                break;
//            case 1:
//                Intent intent = new Intent(MainActivity.this,
//                        BookDetailsActivity.class);
//                itemLauncher.launch(intent);
//                break;
//            case 2:
//                position_id = item.getOrder();
//                Intent intent1 = new Intent(MainActivity.this, ModifyActivity.class);
//                intent1.putExtra("old_name", books.get(position_id).getName());
//                intent1.putExtra("old_price", books.get(position_id).getPrice());
//                imageLauncher_new.launch(intent1);
//                Toast.makeText(MainActivity.this, "商品"+item.getOrder() +"修改", Toast.LENGTH_SHORT).show();
//                break;
//            case 3:
//                Toast.makeText(MainActivity.this, "商品"+item.getOrder()
//                        +"\n加入购物车", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                return super.onContextItemSelected(item);
//        }
//        return true;
//    }
//    public class CustomAdapter extends  RecyclerView.Adapter<CustomAdapter.ViewHolder>{
//        private ArrayList<Book> BOOK_Shop_item;
//        public void removeItem(int position) {
//            BOOK_Shop_item.remove(position);
//            notifyItemRemoved(position);
//        }
//
//        public void editItem(int position, String newName, double newPrice) {
//            Book book = BOOK_Shop_item.get(position);
//            book.setName(newName);
//            book.setPrice(newPrice);
//            notifyItemChanged(position);
//        }
//        public class ViewHolder extends RecyclerView.ViewHolder
//                implements View.OnCreateContextMenuListener {
//            private final TextView item_name;
//            private final TextView item_price;
//            private final ImageView item_image;
//            public ViewHolder(View shop_item_view) {
//                super(shop_item_view);
//                this.item_name = shop_item_view.findViewById(R.id.item_name);
//                this.item_price = shop_item_view.findViewById(R.id.item_price);
//                this.item_image = shop_item_view.findViewById(R.id.image_id);
//                shop_item_view.setOnCreateContextMenuListener(this);
//            }
//            public void onCreateContextMenu(ContextMenu menu, View v,
//                                            ContextMenu.ContextMenuInfo menuInfo){
//                menu.setHeaderTitle("具体操作");
//                menu.add(0,0,this.getAdapterPosition(),"删除"+this.getAdapterPosition());
//                menu.add(0,1,this.getAdapterPosition(),"添加"+this.getAdapterPosition());
//                menu.add(0,2,this.getAdapterPosition(),"修改"+this.getAdapterPosition());
//                menu.add(0,3,this.getAdapterPosition(),"【购买】"+this.getAdapterPosition());
//            }
//            public TextView getItemName(){
//                return item_name;
//            }
//            public TextView getItemPrice(){
//                return item_price;
//            }
//            public ImageView getItemImage(){return item_image;}
//        }
//        public CustomAdapter(ArrayList<Book> dataSet) {
//            BOOK_Shop_item = dataSet;
//        }
//
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = LayoutInflater.from(viewGroup.getContext()).
//                    inflate(R.layout.shop_item_row, viewGroup, false);
//            return new ViewHolder(view);
//        }
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            holder.getItemName().setText(BOOK_Shop_item.get(position).getName());
//            holder.getItemPrice().setText(BOOK_Shop_item.get(position).getPrice()+"元");
//            holder.getItemImage().setImageResource(BOOK_Shop_item.get(position).getImageId());
//        }
//        @Override
//        public int getItemCount() {
//            return BOOK_Shop_item.size();
//        }
//    }
//
//}