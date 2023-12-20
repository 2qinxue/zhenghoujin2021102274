package com.jnu.yidongzuoye.data;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBankInformation {
    String path_of_personality_information = "personality_information";
    ArrayList<personality_information> tasks= new ArrayList<>();
    public ArrayList<personality_information> informationInput(Context context) {
        try
        {
            FileInputStream fileInputStream=context.openFileInput(path_of_personality_information);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            tasks= ( ArrayList<personality_information>)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return tasks;
    }
    public void saveinformations(Context context, ArrayList<personality_information> taskDate) {
        try
        {
            // 删除旧文件
            context.deleteFile(path_of_personality_information);
            FileOutputStream fileOutputStream = context.openFileOutput(path_of_personality_information, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(taskDate);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
