package com.edevelopers.fastecom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *Created by Anubhav Singh on 3/18/2020.
 */

public class DbHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME = "fastecom_name";
    public static final String TABLE_NAME = "Notifications";
    public static final String KEY_notify = "notify";
    public static final String KEY_col1 = "col1";
    public static final String KEY_type = "type";
    public static final String KEY_vchnum = "id";
    public static Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE Notifications ( vchnum INTEGER PRIMARY KEY,vchdate text,type text,branchcd text, notify TEXT,col1)";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}