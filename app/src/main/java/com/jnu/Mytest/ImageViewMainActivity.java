package com.jnu.Mytest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageViewMainActivity extends AppCompatActivity {
    private  Button buttonPrevious ;
    private  Button buttonNext;
    private ImageView imageViewFunny;
    private int p1;

    private int[] imageIDArray = {R.drawable.funny_1, R.drawable.funny_2
            , R.drawable.funny_3, R.drawable.funny_4, R.drawable.funny_1
            , R.drawable.funny_6,R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5
    };
    private int imageIDArrayCurrentIndex;
    public ImageViewMainActivity() {
        imageIDArrayCurrentIndex = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_main);
        buttonPrevious = findViewById(R.id.button_previous);
        buttonNext = findViewById(R.id.button_next);
        imageViewFunny = findViewById(R.id.image_view_funny);
        buttonPrevious.setOnClickListener(new MyButtonClickListener());
        buttonNext.setOnClickListener(new MyButtonClickListener());
    }
    private class MyButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (((Button) view).getText().equals("next")) {
                if(imageIDArrayCurrentIndex < imageIDArray.length - 1)
                    imageIDArrayCurrentIndex+=1;
                System.out.println(imageIDArrayCurrentIndex);
            }
            else {
                if(imageIDArrayCurrentIndex > 0)
                    imageIDArrayCurrentIndex-=1;
                System.out.println(imageIDArrayCurrentIndex);
            }
            imageViewFunny.setImageResource(imageIDArray[imageIDArrayCurrentIndex]);
        }
    }
}

