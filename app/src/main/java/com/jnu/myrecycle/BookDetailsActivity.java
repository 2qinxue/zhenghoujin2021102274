//package com.jnu.myrecycle;
//
//import static com.jnu.myrecycle.R.*;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.jnu.myrecycle.R;
//
//public class shop_item_MainActivity extends AppCompatActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(layout.activity_shop_item_main);
//
//        Button button_OK = findViewById(id.button_OK);
//        button_OK.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                EditText editText = findViewById(id.editTextText_item_name);
//                EditText editText2 = findViewById(id.editTextText2);
//                intent.putExtra("name", "【"+editText.getText().toString()+"】");
//                intent.putExtra("price", editText2.getText().toString()+".0");
//                setResult(Activity.RESULT_OK, intent);
//                shop_item_MainActivity.this.finish();
//            }
//        });
//
//    }
//
//}

package com.jnu.myrecycle;
import static com.jnu.myrecycle.R.id;
import static com.jnu.myrecycle.R.layout;
import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.jnu.myrecycle.data.Book;
import java.util.ArrayList;

public class BookDetailsActivity extends AppCompatActivity {
    int id_item =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_shop_item_main);
        ImageView imageView = findViewById(id.imageView2);
        RecyclerView recyclerView = findViewById(id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button_OK = findViewById(id.button_OK);
        button_OK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText editText = findViewById(id.editTextText_item_name);
                EditText editText2 = findViewById(id.editTextText2);
                intent.putExtra("image_id",id_item);
                intent.putExtra("name", "【" + editText.getText().toString() + "】");
                intent.putExtra("price", editText2.getText().toString() );
                setResult(Activity.RESULT_OK, intent);
                BookDetailsActivity.this.finish();
            }
        });

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("信息安全教学基础（第2版）", 100, R.drawable.book_1));
        books.add(new Book("软件管理项目案例教程（第4版）", 120, R.drawable.book_2));
        books.add(new Book("创新工程实践", 30, R.drawable.book_no_name));
        books.add(new Book("油画", 1024, R.drawable.a_oil_painting));
        class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
            private final ArrayList<Book> books;
            public CustomAdapter(ArrayList<Book> books) {
                this.books = books;
            }
            @NonNull
            @Override
            public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_row, parent, false);
                return new CustomViewHolder(view);
            }
            @Override
            public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
                holder.getItemName().setText(books.get(position).getName());
                holder.getItemPrice().setText(books.get(position).getPrice()+"元");
                holder.getItemImage().setImageResource(books.get(position).getImageId());
            }

            @Override
            public int getItemCount() {return books.size();}

            class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                private TextView itemName, item_price;
                private ImageView item_image;

                public CustomViewHolder(@NonNull View itemView) {
                    super(itemView);
                    itemName = itemView.findViewById(id.item_name);
                    item_price = itemView.findViewById(id.item_price);
                    item_image = itemView.findViewById(id.image_view_book_cover);
                    itemView.setOnClickListener(this);
                }
                public TextView getItemName() {
                    return itemName;
                }
                public int getItemImageId(int position) {
                    return books.get(position).getImageId();
                }
                public TextView getItemPrice() {
                    return item_price;
                }

                public ImageView getItemImage() {
                    return item_image;
                }

                @Override
                public void onClick(View v) {
                    // 获取被点击项的位置
                    int position = getAdapterPosition();
                    // 根据位置获取对应的Book对象
                    Book book = books.get(position);
                    // 显示Book对象的内容，可以使用Toast或者其他方式来显示
                    Toast.makeText(v.getContext(), "Clicked on " + book.getName(), Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(book.getImageId());
                    EditText editText = findViewById(id.editTextText_item_name);
                    editText.setText(book.getName());
                    EditText editText2 = findViewById(id.editTextText2);
                    editText2.setText(book.getPrice()+"");
                    id_item=position;
                }
            }
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {super(itemView);}
        }
        CustomAdapter adapter = new CustomAdapter(books);
        recyclerView.setAdapter(adapter);
    }
}