package com.jnu.bmapplication.Data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataSaver {
    public void Save(Context context, ArrayList<Book> data){
        try {
            //为默认操作模式，代表该文件是私有数据，只能被应用本身访问,在该模式下，写入的内容会覆盖原文件的内容
            FileOutputStream dataStream= context.openFileOutput("ysdata.dat",Context.MODE_PRIVATE);
            ObjectOutputStream out=new ObjectOutputStream(dataStream);
            out.writeObject(data);
            out.close();
            dataStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //反序列化
    @NonNull
    public ArrayList<Book>Load(Context context){
        ArrayList<Book>data=new ArrayList<>();
        try {
            FileInputStream fileIn=context.openFileInput("ysdata.dat");
            ObjectInputStream in=new ObjectInputStream(fileIn);
            data=(ArrayList<Book>)in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
