package com.jnu.yidongzuoye.ownopera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.jnu.yidongzuoye.R;

public class ChangeavatarActivity extends AppCompatActivity {
    ImageButton imageButton,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeavatar);
        imageButton = findViewById(R.id.imageButton2);
        imageButton2 = findViewById(R.id.imageButton3);
        imageButton3 = findViewById(R.id.imageButton6);
        imageButton4 = findViewById(R.id.imageButton7);
        imageButton5 = findViewById(R.id.imageButton8);
        imageButton6 = findViewById(R.id.imageButton9);
        imageButton.setImageResource(R.drawable.book_1);
        imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton2.setImageResource(R.drawable.book_2);
        imageButton2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton3.setImageResource(R.drawable.a_oil_painting);
        imageButton3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton4.setImageResource(R.drawable.p);
        imageButton4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton5.setImageResource(R.drawable.pq);
        imageButton5.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton6.setImageResource(R.drawable.pw);
        imageButton6.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton.setOnClickListener(v -> {
            // 获取点击的ImageButton的图像资源
            int imageResource = R.drawable.book_1;
            Intent intent = new Intent();
            intent.putExtra("image_resource_id", imageResource);
            setResult(Activity.RESULT_OK, intent);
            ChangeavatarActivity.this.finish();
        });
        imageButton2.setOnClickListener(v -> {
            // 获取点击的ImageButton的图像资源
            int imageResource = R.drawable.book_2;
            Intent intent = new Intent();
            intent.putExtra("image_resource_id", imageResource);
            setResult(Activity.RESULT_OK, intent);
            ChangeavatarActivity.this.finish();
        });
        imageButton3.setOnClickListener(v -> {
            // 获取点击的ImageButton的图像资源
            int imageResource = R.drawable.a_oil_painting;
            Intent intent = new Intent();
            intent.putExtra("image_resource_id", imageResource);
            setResult(Activity.RESULT_OK, intent);
            ChangeavatarActivity.this.finish();
        });
        imageButton4.setOnClickListener(v -> {
            // 获取点击的ImageButton的图像资源
            int imageResource = R.drawable.p;
            Intent intent = new Intent();
            intent.putExtra("image_resource_id", imageResource);
            setResult(Activity.RESULT_OK, intent);
            ChangeavatarActivity.this.finish();
        });
        imageButton5.setOnClickListener(v -> {
            // 获取点击的ImageButton的图像资源
            int imageResource = R.drawable.pq;
            Intent intent = new Intent();
            intent.putExtra("image_resource_id", imageResource);
            setResult(Activity.RESULT_OK, intent);
            ChangeavatarActivity.this.finish();
        });
        imageButton6.setOnClickListener(v -> {
            // 获取点击的ImageButton的图像资源
            int imageResource = R.drawable.pw;
            Intent intent = new Intent();
            intent.putExtra("image_resource_id", imageResource);
            setResult(Activity.RESULT_OK, intent);
            ChangeavatarActivity.this.finish();
        });

    }
}