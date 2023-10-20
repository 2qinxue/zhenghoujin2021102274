package com.jnu.myrecycle.data;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBank {
    final String filename="books.data";
    ArrayList<Book> data=new ArrayList<Book>();
    public ArrayList<Book> booksInput(Context context) {
        try
        {
            FileInputStream fileInputStream=context.openFileInput(filename);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            data= ( ArrayList<Book>)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return data;
    }
    public void saveBooks(Context context, ArrayList<Book> books) {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(books);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
