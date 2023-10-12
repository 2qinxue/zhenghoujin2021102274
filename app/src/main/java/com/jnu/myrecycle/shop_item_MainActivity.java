package com.jnu.myrecycle;

import static com.jnu.myrecycle.R.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jnu.myrecycle.R;

public class shop_item_MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout.activity_shop_item_main);

        Button button_OK = findViewById(id.button_OK);
        button_OK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText editText = findViewById(id.editTextText_item_name);
                EditText editText2 = findViewById(id.editTextText2);
                intent.putExtra("name", "【"+editText.getText().toString()+"】");
                intent.putExtra("price", editText2.getText().toString()+".0");
                setResult(Activity.RESULT_OK, intent);
                shop_item_MainActivity.this.finish();
            }
        });

    }

}