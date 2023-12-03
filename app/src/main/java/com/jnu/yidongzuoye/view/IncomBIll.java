package com.jnu.yidongzuoye.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jnu.yidongzuoye.R;
import com.jnu.yidongzuoye.data.Bill;
import com.jnu.yidongzuoye.data.DataBankBill;

import java.util.ArrayList;

public class IncomBIll extends View {

    ArrayList<Bill> bills_list=new ArrayList<>();
    private float averageValue; // 平均值
    public IncomBIll(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        try {
            invalidate();
            bills_list = new DataBankBill().billsInput(this.getContext());
        }
        catch (Exception e)
        {
            bills_list.add(new Bill("null", "0", "0"));
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取视图的宽度和高度
        int width = getWidth();
        int height = getHeight();
        // 绘制矩形背景
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, width, height, backgroundPaint);

        // 绘制x轴刻度线和标签
        int xTickCount = 8; // x轴刻度数量
        int xStart = 80; // x轴起始位置
        int xEnd = width - 100; // x轴结束位置
        int xInterval = (xEnd - xStart) / (xTickCount - 1); // x轴刻度间隔

        Paint xTickPaint = new Paint();
        xTickPaint.setColor(Color.BLACK);
        xTickPaint.setStrokeWidth(2);

        Paint xLabelPaint = new Paint();
        xLabelPaint.setColor(Color.BLACK);
        xLabelPaint.setTextSize(30);


        String[][] xLabels = {{" ", "一", "二", "三", "四", "五", "六", "七"},{" ","第一周","第二周","第三周","第四周","第五周","第六周","第七周"}
                ,{" ","第一月","第二月","第三月","第四月","第五月","第六月","第七月","第八月","第九月","第十月","第十一月","第十二月"},
                {" ","2015","2016","2017","2018","2019","2020","2021","2022","2023"}};
        int position=0;

        for (int i = 1; i < xTickCount; i++) {
            int x = xStart + i * xInterval;
            // 绘制刻度线
            canvas.drawLine(x, height- 60, x, height - 80, xTickPaint);
            // 绘制标签
            float labelWidth = xLabelPaint.measureText(xLabels[position][i]);
            canvas.drawText(xLabels[position][i], x - labelWidth / 2, getHeight() - 20, xLabelPaint);
        }

        // 绘制y轴刻度线和标签
        int yTickCount = 11; // y轴刻度数量
        int yStart = getHeight() - 60; // y轴起始位置
        int yEnd = 80; // y轴结束位置
        int yInterval = (yStart - yEnd) / (yTickCount - 1); // y轴刻度间隔
        int yMax = 200; // y轴最大值
        Paint yTickPaint = new Paint();
        yTickPaint.setColor(Color.BLACK);
        yTickPaint.setStrokeWidth(1);

        Paint yLabelPaint = new Paint();
        yLabelPaint.setColor(Color.RED);
        yLabelPaint.setTextSize(30);

        for (int i = 0; i < yTickCount; i++) {
            int y = yStart - i * yInterval;
            // 绘制刻度线
            canvas.drawLine(xStart - 20, y, width - 60, y, yTickPaint);
            // 绘制标签
            String label = String.valueOf(i * (yMax / (yTickCount - 1)));
            float labelWidth = yLabelPaint.measureText(label);
            canvas.drawText(label, xStart - 25 - labelWidth, y + 10, yLabelPaint);
        }

        // 绘制坐标轴x和y
        Paint axisPaint = new Paint();
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStrokeWidth(5);
        // 绘制x轴
        canvas.drawLine(xStart-50, height - 60, width - 50, height - 60, axisPaint);
        // 绘制y轴
        canvas.drawLine(xStart, yEnd, xStart, yStart+40, axisPaint);

        // 绘制折线图
        if(bills_list.size()!=0) {
            Paint linePaint = new Paint();
            linePaint.setColor(Color.BLUE);
            linePaint.setStrokeWidth(3);

            ArrayList<Bill>bill_temp=new ArrayList<>();
            int count_comsumption = 0;
            for (Bill bill : bills_list) {
                if (bill.getBillScore().charAt(0) == '+') {
                    bill_temp.add(bill);
                    count_comsumption++;
                    averageValue += Double.parseDouble(bill.getBillScore().substring(1));
                }
            }
            if(count_comsumption!=0) {
                averageValue = averageValue / count_comsumption;
                int numPoints = count_comsumption; // 数据点的数量
                float yScale = (yStart - yEnd) / yMax; // y轴上的比例尺
                double pre_data=0;
                int posiT=numPoints>7?numPoints-7:0;
                for (int i = posiT, j = 0; i < numPoints; i++,j++) {
                    double data =Double.parseDouble(bill_temp.get(i).getBillScore().substring(1));
                    float x = xStart + (j + 1) * xInterval;
                    float y = (float) (height - 60 - data * yScale);
                    canvas.drawCircle(x, y, 10, linePaint); // 绘制数据点的圆圈
                    on_paint(canvas, String.valueOf(data), 30, Color.BLUE, x + 20, y - 20);
                    if (j > 0) {
                        float prevX = xStart + j * xInterval;
                        float prevY = (float) (height - 60 - pre_data * yScale);
                        pre_data=data;
                        canvas.drawLine(prevX, prevY, x, y, linePaint); // 绘制数据点之间的连线
                    }
                    else
                        pre_data=data;
                }
            }
        }
        //label
        on_paint(canvas, "收入", 60, R.color.purple_200, 100, 70);
        on_paint(canvas, "平均收入:"+averageValue, 60,R.color.purple_200, width-600, 70);

    }
    public void on_paint(Canvas canvas, String text,int size,int _color,float x_,float y_) {
        Paint paint = new Paint();
        paint.setColor(_color);
        paint.setTextSize(size);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, x_, y_, paint);
    }
}