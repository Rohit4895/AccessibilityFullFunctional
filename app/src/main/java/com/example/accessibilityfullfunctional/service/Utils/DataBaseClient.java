package com.example.accessibilityfullfunctional.service.Utils;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DataBaseClient {
    private Context context;
    private static DataBaseClient dataBaseInstance;
    private MainDataBase mainDataBase;

    private DataBaseClient(Context context){
        this.context = context;
        mainDataBase = Room.databaseBuilder(context,MainDataBase.class,"DataBase").build();
    }

    public static synchronized DataBaseClient getInstance(Context context){
        if (dataBaseInstance == null){
            dataBaseInstance = new DataBaseClient(context);
        }
        return dataBaseInstance;
    }

    public MainDataBase getMainDataBase(){
        return mainDataBase;
    }
}
