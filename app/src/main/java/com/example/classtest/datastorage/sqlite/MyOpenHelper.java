package com.example.classtest.datastorage.sqlite;

import android.animation.TypeEvaluator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @description SQLite帮助类
 * Created by L on 2016/8/29.
 */
public class MyOpenHelper extends SQLiteOpenHelper{
    //数据库版本
    public static final int DATABASE_VERSION = 1;
    //数据库名称
    public static final String DATABASE_NAME="phone.db";


    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表
        db.execSQL(TypeEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //删除原表
        db.execSQL(TypeEntry.SQL_CREATE_TABLE);
        //建表
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}
