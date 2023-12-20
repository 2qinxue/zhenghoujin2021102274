package com.jnu.yidongzuoye.view;

import static com.jnu.yidongzuoye.MainActivity.isAddTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.jnu.yidongzuoye.R;
import com.jnu.yidongzuoye.data.Bill;
import com.jnu.yidongzuoye.data.DataBankBill;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CustomCurveChart extends View {
    String[][] xLabels = {{" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}, {" ", "周一", "周二", "周三", "周四", "周五", "周六", "周日"}
            , {" ", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"},
            {" ", "2017", "2018", "2019", "2020", "2021", "2022", "2023"}};
    ArrayList<Bill> bills_list;
    String []text_={" ","周","月","年"};

    public static int currentCustomposition;

    // 构造函数
    public CustomCurveChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private  void init() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<ArrayList<Bill>> future = executor.submit(() -> {
            try {
                return new DataBankBill().billsInput(getContext());
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        });

        try {
            bills_list = future.get(); // 获取异步任务的结果
            // 在这里进行初始化后续操作，如更新 UI 或者执行其他逻辑
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown(); // 关闭 executor
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if(isAddTask)
        {
            init();
        }
        // 获取视图的宽度和高度
        int width = getWidth();
        int height = getHeight();
        // 绘制矩形背景
        @SuppressLint("DrawAllocation") Paint backgroundPaint = new Paint();
        backgroundPaint.setColor("#FFFFFF".hashCode());
        canvas.drawRect(0, 0, width, height, backgroundPaint);

        int position =currentCustomposition;
        // 绘制x轴刻度线和标签
        int xTickCount = xLabels[position].length; // x轴刻度数量
        int xStart = 80; // x轴起始位置
        int xEnd = width - 100; // x轴结束位置
        int xInterval = (xEnd - xStart) / (xTickCount - 1); // x轴刻度间隔

        @SuppressLint("DrawAllocation") Paint xTickPaint = new Paint();
        xTickPaint.setColor(Color.BLACK);
        xTickPaint.setStrokeWidth(2);

        @SuppressLint("DrawAllocation") Paint xLabelPaint = new Paint();
        xLabelPaint.setColor(Color.BLACK);
        xLabelPaint.setTextSize(30);


        for (int i = 1; i < xTickCount; i++) {
            int x = xStart + i * xInterval;
            // 绘制刻度线
            canvas.drawLine(x, height - 60, x, height - 80, xTickPaint);
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

        @SuppressLint("DrawAllocation") Paint yTickPaint = new Paint();
        yTickPaint.setColor(Color.BLACK);
        yTickPaint.setStrokeWidth(1);

        @SuppressLint("DrawAllocation") Paint linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(3);

        // 平均值
        float averageValue;
        if (position == 0) {
            averageValue = 0;
            // 绘制折线图
            if (bills_list.size() != 0) {

                @SuppressLint("DrawAllocation") ArrayList<Bill> bill_temp = new ArrayList<>();
                int count_comsumption = 0;
                for (Bill bill : bills_list) {
                    if (bill.getBillScore().charAt(0) == '-') {
                        bill_temp.add(bill);
                        count_comsumption++;
                        averageValue -= Double.parseDouble(bill.getBillScore());
                    }
                }
                if (count_comsumption != 0) {
                    on_paint(canvas, text_[position]+"支出:"+new Formatter().format("%.2f", averageValue), 45, R.color.red, 100, 70);
                    averageValue = averageValue / count_comsumption;
                    float yScale = (yStart+0.0f - yEnd) / yMax; // y轴上的比例尺
                    double pre_data = 0;
                    int posiT = count_comsumption > 12 ? count_comsumption - 12 : 0;
                    for (int i = posiT, j = 0; i < count_comsumption; i++, j++) {
                        double data = Double.parseDouble(bill_temp.get(i).getBillScore().substring(1));
                        float x = xStart + (j + 1) * xInterval;
                        float y = (float) (yStart - data * yScale);
                        canvas.drawCircle(x, y, 10, linePaint); // 绘制数据点的圆圈
                        on_paint(canvas, String.valueOf(data), 30, Color.RED, x + 20, y - 20);
                        if (j > 0) {
                            float prevX = xStart + j * xInterval;
                            float prevY = (float) (yStart - pre_data * yScale);
                            pre_data = data;
                            canvas.drawLine(prevX, prevY, x, y, linePaint); // 绘制数据点之间的连线
                        } else
                            pre_data = data;
                    }
                }
            }
            Paint yLabelPaint = new Paint();
            yLabelPaint.setColor(Color.RED);
            yLabelPaint.setTextSize(30);

            for (int i = 0; i < yTickCount; i++) {
                int y = yStart - i * yInterval;
                // 绘制刻度线
                canvas.drawLine(xStart, y, width - 60, y, yTickPaint);
                // 绘制标签
                String label = String.valueOf(i * (yMax / (yTickCount - 1)));
                float labelWidth = yLabelPaint.measureText(label);
                canvas.drawText(label, xStart - 15 - labelWidth, y + 10, yLabelPaint);
            }

            // 绘制坐标轴x和y
            Paint axisPaint = new Paint();
            axisPaint.setColor(Color.BLACK);
            axisPaint.setStrokeWidth(5);
            // 绘制x轴
            canvas.drawLine(xStart - 50, yStart, width - 50, yStart, axisPaint);
            // 绘制y轴
            canvas.drawLine(xStart, yEnd, xStart, yStart + 40, axisPaint);
            //label
            on_paint(canvas, text_[position]+"平均支出:" + averageValue, 45, R.color.red, width - 600, 70);

        } else if (position == 1) {
            averageValue = 0;
            // 绘制折线图
            if (bills_list.size() != 0) {
                int[] arrayweekday = {0, 0, 0, 0, 0, 0, 0};
                double[] averageValue_week = new double[7];
                int count_comsumption = 0;
                for (Bill bill : bills_list) {
                    if (bill.getBillScore().charAt(0) == '-') {
                        averageValue += Double.parseDouble(bill.getBillScore().substring(1));
                        switch (bill.getBillTime().substring(20)) {
                            case "星期一":
                                averageValue_week[0] += Double.parseDouble(bill.getBillScore().substring(1));
                                arrayweekday[0] = 1;
                                break;
                            case "星期二":
                                averageValue_week[1] += Double.parseDouble(bill.getBillScore().substring(1));
                                arrayweekday[1] = 1;
                                break;
                            case "星期三":
                                averageValue_week[2] += Double.parseDouble(bill.getBillScore().substring(1));
                                arrayweekday[2] = 1;
                                break;
                            case "星期四":
                                averageValue_week[3] += Double.parseDouble(bill.getBillScore().substring(1));
                                arrayweekday[3] = 1;
                                break;
                            case "星期五":
                                averageValue_week[4] += Double.parseDouble(bill.getBillScore().substring(1));
                                arrayweekday[4] = 1;
                                break;
                            case "星期六":
                                averageValue_week[5] += Double.parseDouble(bill.getBillScore().substring(1));
                                arrayweekday[5] = 1;
                                break;
                            default:
                                averageValue_week[6] += Double.parseDouble(bill.getBillScore().substring(1));
                                arrayweekday[6] = 1;
                                break;
                        }
                    }
                }
                for (int i = 0; i < arrayweekday.length; i++) {
                    yMax = Math.max(yMax,((int)(averageValue_week[i]/201)+1)*200);
                    count_comsumption += arrayweekday[i];
                }
                if (count_comsumption != 0) {
                    on_paint(canvas, text_[position]+"支出:"+new Formatter().format("%.2f", averageValue), 45, R.color.red, 100, 70);
                    averageValue = averageValue / arrayweekday.length;
                    float yScale = (yStart+0.0f - yEnd) / yMax; // y轴上的比例尺
                    double pre_data = 0;
                    int pre_index=0;
                    for (int i = 0, j = 0; i < arrayweekday.length; i++) {
                        if (arrayweekday[i] != 0) {
                            double data = averageValue_week[i];
                            float x = xStart + (i + 1) * xInterval;
                            float y = (float) (yStart - data * yScale);
                            canvas.drawCircle(x, y, 10, linePaint); // 绘制数据点的圆圈
                            on_paint(canvas, String.valueOf(data), 30, Color.RED, x + 20, y - 20);
                            if (j > 0) {
                                float prevX = xStart + pre_index * xInterval;
                                float prevY = (float) (yStart- pre_data * yScale);
                                canvas.drawLine(prevX, prevY, x, y, linePaint); // 绘制数据点之间的连线
                            }
                            pre_data = data;
                            j++;
                            pre_index = i+1;
                        }
                    }
                }
            }
            Paint yLabelPaint = new Paint();
            yLabelPaint.setColor(Color.RED);
            yLabelPaint.setTextSize(30);

            for (int i = 0; i < yTickCount; i++) {
                int y = yStart - i * yInterval;
                // 绘制刻度线
                canvas.drawLine(xStart, y, width - 60, y, yTickPaint);
                // 绘制标签
                String label = String.valueOf(i * (yMax / (yTickCount - 1)));
                float labelWidth = yLabelPaint.measureText(label);
                canvas.drawText(label, xStart - 15 - labelWidth, y + 10, yLabelPaint);
            }

            // 绘制坐标轴x和y
            Paint axisPaint = new Paint();
            axisPaint.setColor(Color.BLACK);
            axisPaint.setStrokeWidth(5);
            // 绘制x轴
            canvas.drawLine(xStart - 50, yStart, width - 50, yStart, axisPaint);
            // 绘制y轴
            canvas.drawLine(xStart, yEnd, xStart, yStart + 40, axisPaint);
            //label
            on_paint(canvas, text_[position]+"平均支出:" + averageValue, 45, R.color.red, width - 600, 70);


        } else if (position == 2) {
            averageValue = 0;
            // 绘制折线图
            if (bills_list.size() != 0) {
                int[] arrayweekday = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                double[] averageValue_week = new double[12];
                int count_comsumption = 0;
                for (Bill bill : bills_list) {
                    if (bill.getBillScore().charAt(0) == '-') {
                        averageValue += Double.parseDouble(bill.getBillScore().substring(1));
                        if (bill.getBillTime().startsWith("01", 5)) {
                            averageValue_week[0] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[0] = 1;
                        } else if (bill.getBillTime().startsWith("02", 5)) {
                            averageValue_week[1] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[1] = 1;
                        } else if (bill.getBillTime().startsWith("03", 5)) {
                            averageValue_week[2] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[2] = 1;
                        } else if (bill.getBillTime().startsWith("04", 5)) {
                            averageValue_week[3] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[3] = 1;
                        } else if (bill.getBillTime().startsWith("05", 5)) {
                            averageValue_week[4] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[4] = 1;
                        } else if (bill.getBillTime().startsWith("06", 5)) {
                            averageValue_week[5] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[5] = 1;
                        } else if (bill.getBillTime().startsWith("07", 5)) {
                            averageValue_week[6] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[6] = 1;
                        } else if (bill.getBillTime().startsWith("08", 5)) {
                            averageValue_week[7] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[7] = 1;
                        } else if (bill.getBillTime().startsWith("09", 5)) {
                            averageValue_week[8] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[8] = 1;
                        } else if (bill.getBillTime().startsWith("10", 5)) {
                            averageValue_week[9] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[9] = 1;
                        } else if (bill.getBillTime().startsWith("11", 5)) {
                            averageValue_week[10] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[10] = 1;
                        } else if (bill.getBillTime().startsWith("12", 5)) {
                            averageValue_week[11] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[11] = 1;
                        }
                    }
                }
                for (int i = 0; i < arrayweekday.length; i++) {
                    yMax = Math.max(yMax,((int)(averageValue_week[i]/201)+1)*200);
                    count_comsumption += arrayweekday[i];
                }
                if (count_comsumption != 0) {
                    on_paint(canvas, text_[position]+"支出:"+new Formatter().format("%.2f", averageValue), 45, R.color.red, 100, 70);
                    averageValue = averageValue / arrayweekday.length;
                    float yScale = (yStart+0.0f - yEnd) / yMax; // y轴上的比例尺
                    double pre_data = 0;
                    int pre_index = 0;
                    for (int i = 0, j = 0; i < arrayweekday.length; i++) {
                        if (arrayweekday[i] != 0) {
                            double data = averageValue_week[i];
                            float x = xStart + (i + 1) * xInterval;
                            float y = (float) (yStart - data * yScale);
                            canvas.drawCircle(x, y, 10, linePaint); // 绘制数据点的圆圈
                            on_paint(canvas, String.valueOf(data), 30, Color.RED, x + 20, y - 20);
                            if (j > 0) {
                                float prevX = xStart + pre_index * xInterval;
                                float prevY = (float) (height - 60 - pre_data * yScale);
                                canvas.drawLine(prevX, prevY, x, y, linePaint); // 绘制数据点之间的连线
                            }
                            pre_data = data;
                            j++;
                            pre_index = i+1;
                        }
                    }
                }
            }
            Paint yLabelPaint = new Paint();
            yLabelPaint.setColor(Color.RED);
            yLabelPaint.setTextSize(30);

            for (int i = 0; i < yTickCount; i++) {
                int y = yStart - i * yInterval;
                // 绘制刻度线
                canvas.drawLine(xStart, y, width - 60, y, yTickPaint);
                // 绘制标签
                String label = String.valueOf(i * (yMax / (yTickCount - 1)));
                float labelWidth = yLabelPaint.measureText(label);
                canvas.drawText(label, xStart - 15 - labelWidth, y + 10, yLabelPaint);
            }

            // 绘制坐标轴x和y
            Paint axisPaint = new Paint();
            axisPaint.setColor(Color.BLACK);
            axisPaint.setStrokeWidth(5);
            // 绘制x轴
            canvas.drawLine(xStart - 50, yStart, width - 50, yStart, axisPaint);
            // 绘制y轴
            canvas.drawLine(xStart, yEnd, xStart, yStart + 40, axisPaint);
            //label
            on_paint(canvas, text_[position]+"平均支出:" + averageValue, 45, R.color.red, width - 600, 70);

        } else {
            averageValue = 0;
            // 绘制折线图
            if (bills_list.size() != 0) {
                int[] arrayweekday = {0, 0, 0, 0, 0, 0, 0};
                double[] averageValue_week = new double[7];
                int count_comsumption = 0;
                for (Bill bill : bills_list) {
                    if (bill.getBillScore().charAt(0) == '-') {
                        averageValue += Double.parseDouble(bill.getBillScore().substring(1));
                        if (bill.getBillTime().startsWith("2017")) {
                            averageValue_week[0] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[0] = 1;
                        } else if (bill.getBillTime().startsWith("2018")) {
                            averageValue_week[1] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[1] = 1;
                        } else if (bill.getBillTime().startsWith("2019")) {
                            averageValue_week[2] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[2] = 1;
                        } else if (bill.getBillTime().startsWith("2020")) {
                            averageValue_week[3] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[3] = 1;
                        } else if (bill.getBillTime().startsWith("2021")) {
                            averageValue_week[4] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[4] = 1;
                        } else if (bill.getBillTime().startsWith("2022")) {
                            averageValue_week[5] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[5] = 1;
                        } else if (bill.getBillTime().startsWith("2023")) {
                            averageValue_week[6] += Double.parseDouble(bill.getBillScore().substring(1));
                            arrayweekday[6] = 1;
                        }
                    }
                }
                for (int i = 0; i < arrayweekday.length; i++) {
                    yMax = Math.max(yMax,((int)(averageValue_week[i]/201)+1)*200);
                    count_comsumption += arrayweekday[i];
                }
                if (count_comsumption != 0) {
                    on_paint(canvas, text_[position]+"支出:"+new Formatter().format("%.2f", averageValue), 45, R.color.red, 100, 70);
                    averageValue = averageValue / arrayweekday.length;
                    float yScale = (yStart+0.0f - yEnd) / yMax; // y轴上的比例尺
                    double pre_data = 0;
                    int pre_index = 0;
                    for (int i = 0, j = 0; i < arrayweekday.length; i++) {
                        if (arrayweekday[i] != 0) {
                            double data = averageValue_week[i];
                            float x = xStart + (i + 1) * xInterval;
                            float y = (float) (yStart - data * yScale);
                            canvas.drawCircle(x, y, 10, linePaint); // 绘制数据点的圆圈
                            on_paint(canvas, String.valueOf(data), 30, Color.RED, x + 20, y - 20);
                            if (j > 0) {
                                float prevX = xStart + pre_index * xInterval;
                                float prevY = (float) (yStart - pre_data * yScale);
                                canvas.drawLine(prevX, prevY, x, y, linePaint); // 绘制数据点之间的连线
                            }
                            pre_data = data;
                            j++;
                            pre_index = i+1;
                        }
                    }

                }
            }
            Paint yLabelPaint = new Paint();
            yLabelPaint.setColor(Color.RED);
            yLabelPaint.setTextSize(30);

            for (int i = 0; i < yTickCount; i++) {
                int y = yStart - i * yInterval;
                // 绘制刻度线
                canvas.drawLine(xStart, y, width - 60, y, yTickPaint);
                // 绘制标签
                String label = String.valueOf(i * (yMax / (yTickCount - 1)));
                float labelWidth = yLabelPaint.measureText(label);
                canvas.drawText(label, xStart - 15 - labelWidth, y + 10, yLabelPaint);
            }

            // 绘制坐标轴x和y
            Paint axisPaint = new Paint();
            axisPaint.setColor(Color.BLACK);
            axisPaint.setStrokeWidth(5);
            // 绘制x轴
            canvas.drawLine(xStart - 50, yStart, width - 50, yStart, axisPaint);
            // 绘制y轴
            canvas.drawLine(xStart, yEnd, xStart, yStart + 40, axisPaint);
            //label
            on_paint(canvas, text_[position]+"平均支出:" + averageValue, 45, R.color.red, width - 600, 70);
        }

        invalidate();

    }


    public void on_paint(Canvas canvas, String text, int size, int _color, float x_, float y_) {
        Paint paint = new Paint();
        paint.setColor(_color);
        paint.setTextSize(size);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, x_, y_, paint);
    }
}