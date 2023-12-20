package com.jnu.yidongzuoye.data;

import static com.jnu.yidongzuoye.MainActivity.isAddTask;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBankBill {

    ArrayList<Bill> data= new ArrayList<>();

    public  static String Bill_STORE_FILENAME="bill_store.data";
    public ArrayList<Bill> billsInput(Context context) {
        try
        {
            FileInputStream fileInputStream=context.openFileInput(Bill_STORE_FILENAME);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            data= ( ArrayList<Bill>)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return data;
    }
    public void saveBills(Context context, ArrayList<Bill> bills) {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput(Bill_STORE_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(bills);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        isAddTask=true;
    }
}
