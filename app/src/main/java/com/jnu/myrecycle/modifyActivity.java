package com.jnu.myrecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class modifyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifly_main);
        Button button_OK = findViewById(R.id.button8);
        button_OK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText editText = findViewById(R.id.edit1);
                EditText editText2 = findViewById(R.id.edit2);
                intent.putExtra("new_name", "【" + editText.getText().toString() + "】");
                intent.putExtra("new_price", editText2.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                modifyActivity.this.finish();
            }
        });
    }
}