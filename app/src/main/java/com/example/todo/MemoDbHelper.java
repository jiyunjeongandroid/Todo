package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDbHelper extends SQLiteOpenHelper {
    private static MemoDbHelper sInstance;

    public static final int DB_NERSION = 1;
    public static final String DB_NAME = "Memo.db";
    private static final String SQL_CREATE_ENTRIES =
            String.format (
                    "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                    Memo.MemoEntry.TABLE_NAME,
                    Memo.MemoEntry._ID,
                    Memo.MemoEntry.COLUMN_NAME_TITLE,
                    Memo.MemoEntry.COLUMN_NAME_CONTENT,
                    Memo.MemoEntry.COLUMN_NAME_DATE,
                    Memo.MemoEntry.COLUMN_NAME_CAMPUS_OR_LIFE,
                    Memo.MemoEntry.COLUMN_NAME_IMPORTANCE
            );

    private static final String SQL_DELETE_ENTRIES =
            "DROP TAVLE IF EXITS " + Memo.MemoEntry.TABLE_NAME;

    static MemoDbHelper getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new MemoDbHelper (context);
        }
        return sInstance;
    }

    public MemoDbHelper(Context context) {
        super (context, DB_NAME, null, DB_NERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL (SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL (SQL_DELETE_ENTRIES);
        onCreate (db);
    }
}
