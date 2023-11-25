package com.jnu.myrecycle.ownopera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jnu.myrecycle.MainActivity;
import com.jnu.myrecycle.R;

public class ChangeavatarActivity extends AppCompatActivity {
    ImageButton imageButton,imageButton2,imageButton3,imageButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeavatar);
        imageButton = findViewById(R.id.imageButton2);
        imageButton2 = findViewById(R.id.imageButton3);
        imageButton3 = findViewById(R.id.imageButton6);
        imageButton4 = findViewById(R.id.imageButton7);
        imageButton.setImageResource(R.drawable.book_1);
        imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton2.setImageResource(R.drawable.book_2);
        imageButton2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton3.setImageResource(R.drawable.a_oil_painting);
        imageButton3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton4.setImageResource(R.drawable.p);
        imageButton4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取点击的ImageButton的图像资源
                int imageResource = R.drawable.book_1;
                finish();
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取点击的ImageButton的图像资源
                int imageResource = R.drawable.book_2;
                finish();
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取点击的ImageButton的图像资源
                int imageResource = R.drawable.a_oil_painting;
                finish();
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取点击的ImageButton的图像资源
                int imageResource = R.drawable.p;
                finish();
            }
        });
    }
}