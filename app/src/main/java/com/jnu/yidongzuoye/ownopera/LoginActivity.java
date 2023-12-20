package com.jnu.yidongzuoye.ownopera;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jnu.yidongzuoye.MainActivity;
import com.jnu.yidongzuoye.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = findViewById(R.id.button_login);
        button.setText("登录");
        button.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        });
        int img_resource = getIntent().getIntExtra("img_id", R.drawable.p);
        ImageView imageView = findViewById(R.id.imageView_login);
        imageView.setImageResource(img_resource);
        TextView textView = findViewById(R.id.textView);
        String name = getIntent().getStringExtra("ac_name");
        textView.setText(name);
    }
}