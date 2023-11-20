package com.jnu.myrecycle.data;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBankPrize {
    final String filename="prize.data";
    ArrayList<Prize> prize=new ArrayList<Prize>();
    public ArrayList<Prize> tasksInput(Context context) {
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
    public void savePrizes(Context context, ArrayList<Prize> prizeDate) {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(prizeDate);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}