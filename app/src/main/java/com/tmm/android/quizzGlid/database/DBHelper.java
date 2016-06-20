package com.tmm.android.quizzGlid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tmm.android.quizzGlid.database.tables.LanguageTable;
import com.tmm.android.quizzGlid.database.tables.QuestionTable;

/**
 * Created by bilel on 08/05/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    // database name
    private static final String DATABASE_NAME = "my-Quizz.db";
    // data base version
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            QuestionTable.onCreate(db);
            LanguageTable.onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;"); // Active Foreign Key
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        QuestionTable.onUpgrade(db, oldVersion, newVersion);
        LanguageTable.onUpgrade(db, oldVersion, newVersion);
    }

}