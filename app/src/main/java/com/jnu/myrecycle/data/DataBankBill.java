package com.jnu.myrecycle.data;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBankBill {

    ArrayList<Bill> data=new ArrayList<Bill>();
    public ArrayList<Bill> billsInput(Context context, String file_name) {
        try
        {
            FileInputStream fileInputStream=context.openFileInput(file_name);
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
    public void saveBills(Context context, ArrayList<Bill> bills, String file_name) {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput(file_name, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(bills);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
