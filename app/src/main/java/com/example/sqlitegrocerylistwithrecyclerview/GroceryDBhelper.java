package com.example.sqlitegrocerylistwithrecyclerview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlitegrocerylistwithrecyclerview.GroceryContracts.*;

import androidx.annotation.Nullable;

public class GroceryDBhelper extends SQLiteOpenHelper {
    /*
    Define SQL database name and version (Always)
     */
    public static final String DATABASE_NAME = "grocerylist.db";
    public static final int DATABASE_VERSION = 1;

    /*
        Setup the DATABASE
     */
    public GroceryDBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
    SQL DB queries
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DB_TABLE = "CREATE TABLE " + GroceryEntry.Table_Name + " (" + GroceryEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + GroceryEntry.Column_Name + " TEXT NOT NULL, "
                + GroceryEntry.Column_Amount + " INTEGER NOT NULL, "
                + GroceryEntry.Column_TimeStamp + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";

        db.execSQL(SQL_CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Table if already in DB.
        db.execSQL("DROP TABLE IF EXISTS " + GroceryEntry.Table_Name);
        onCreate(db);
    }
}
