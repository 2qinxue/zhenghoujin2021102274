package com.jnu.myrecycle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.jnu.myrecycle.R;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Statistic extends View {

    private List<Float> data; // 折线数据
    private float averageValue; // 平均值

    // 构造函数
    public Statistic(Context context) {
        super(context);
    }
    public Statistic(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取视图的宽度和高度
        int width = getWidth();
        int height = getHeight();

        // 绘制白色矩形背景
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, width, height, backgroundPaint);

        // 绘制坐标轴x和y
        Paint axisPaint = new Paint();
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStrokeWidth(5);

        // 绘制x轴
        canvas.drawLine(20, height-60, width-60, height -60, axisPaint);
        // 绘制y轴
        canvas.drawLine(60, 60, 60, height, axisPaint);

    }
}
