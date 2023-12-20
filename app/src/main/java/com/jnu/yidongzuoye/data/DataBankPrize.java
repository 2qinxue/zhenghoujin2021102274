package com.jnu.yidongzuoye.data;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBankPrize {
    public static String PRIZE_DATA_FILE_NAME="prize.data";
    public static String PRIZE_STORE_DATA_FILE_NAME="prize_store.data";
    ArrayList<Prize> prize= new ArrayList<>();
    public ArrayList<Prize> prizesInput(Context context, String filename) {
        try
        {
            FileInputStream fileInputStream=context.openFileInput(filename);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            prize= ( ArrayList<Prize>)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return prize;
    }
    public void savePrizes(Context context, ArrayList<Prize> prizeDate, String filepath) {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput(filepath, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(prizeDate);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
