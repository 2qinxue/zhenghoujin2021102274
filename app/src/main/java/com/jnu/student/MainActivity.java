package com.jnu.student;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    private int[] imageIDArray={R.drawable.p1,R.drawable.p2,
//            R.drawable.p3,R.drawable.p4,R.drawable.p5};
    private Button myButton3,myButton4,myButton5;
//    private int imageIDArrayCurrentIndex;
//    private ImageView imageViewFunny;
//    public MainActivity()
//    {
//        imageIDArrayCurrentIndex=0;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.i("MainActivity","enter onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=new TextView(this);
        TextView textView1=findViewById(R.id.text_vciew_hellow_world);
        textView.setText(textView1.getText().toString());
        Button myButton = findViewById(R.id.button2);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myButton.setText("你玩了");
                Toast.makeText(MainActivity.this,"中病毒啦",Toast.LENGTH_SHORT).show();
            }
        });
        Button myButton1 = findViewById(R.id.button);
        myButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myButton1.setText("点击了");
            }
        });

        Button myButton2 = findViewById(R.id.button4);
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.textView2);
                TextView textView1=findViewById(R.id.textView3);
                String temp=textView1.getText().toString();
                String temp1=textView.getText().toString();
                textView.setText(temp);
                textView1.setText(temp1);
                AlertDialog dialog=new AlertDialog.Builder(MainActivity.this).create();
                dialog.setTitle("交换成功");
                dialog.show();
                Toast.makeText(MainActivity.this, "交换成功", Toast.LENGTH_SHORT).show();
            }
        });

        myButton3=findViewById(R.id.button3);
        myButton4=findViewById(R.id.button5);
        myButton5=findViewById(R.id.button6);

        myButton4.setOnClickListener(this);
        myButton5.setOnClickListener(this);

//        imageViewFunny=findViewById(R.id.imageView);
    }
    public void handleButtonClick(View view) {

        myButton3.setText("Button3已经点击了");
        // 处理按钮点击事件的代码
    }
    public void onClick(View v) {
        if(v.getId()==R.id.button5){
            myButton4.setText("Button5已经点击了");

            }
        else if(v.getId()==R.id.button6){
            myButton5.setText("Button6已经点击了");
        }
    }
//    ImageView imageView;
//    private class ButtonOnClickListener implements View.OnClickListener{
//        @Override
//        public void onClick(View v) {
//            if (((Button)v).getText()=="按钮5"){
//                imageIDArrayCurrentIndex ++;
//            }
//            else if(v==myButton5)
//            {
//                imageIDArrayCurrentIndex--;
////                imageView.setImageResource(imageIDArray[imageIDArrayCurrentIndex]);
//            }
//            imageIDArrayCurrentIndex = imageIDArrayCurrentIndex % imageIDArray.length;
//        }
//    }
}




