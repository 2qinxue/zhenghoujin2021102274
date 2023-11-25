package com.jnu.myrecycle.data;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class DataBankTask {

    public static String DAILY_TASK_DATA_FILE_NAME ="daily_tasks.data";
    public static String FINISH_TASK_DATA_FILE_NAME="finished_tasks.data";
    public static String WEEKLY_TASK_FILE_NAME="weekly_tasks.data";
    public static String COMMON_TASK_FILE_NAME="common_tasks.data";
    public static String INSTANCE_TASK_filename="instance_tasks.data";
    public  static String TASK_STORE_filename="tasks_store.data";

    ArrayList<Task> tasks=new ArrayList<Task>();
    public ArrayList<Task> tasksInput(Context context, String filename) {
        try
        {
            FileInputStream fileInputStream=context.openFileInput(filename);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            tasks= ( ArrayList<Task>)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return tasks;
    }
    public void saveTasks(Context context, ArrayList<Task> taskDate, String filename) {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(taskDate);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
