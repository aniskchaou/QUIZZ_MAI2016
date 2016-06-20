package com.tmm.android.quizzGlid.database.tables;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.tmm.android.quizzGlid.R;
import com.tmm.android.quizzGlid.database.DBContentProvider;
import com.tmm.android.quizzGlid.quiz.Constants;
import com.tmm.android.quizzGlid.util.Utility;

public class LanguageTable implements BaseColumns {

    // Records database table
    public static final String TABLE_LANGUAGE = "language";

    // table records fields
    public static final String KEY = "KEY";
    public static final String LANG = "LANG";
    public static final String VALUE = "VALUE";

    // records table creation statement
    private static final String CREATE_LANGUAGE_TABLE = "CREATE TABLE "
            + TABLE_LANGUAGE + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY + " TEXT NOT NULL, " +
            LANG + " INTEGER, " +
            VALUE + " TEXT NOT NULL);" ;


    // info for content provider
    public static final String CONTENT_PATH = "language";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.testprovider.language";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.testprovider.language";

    public static final String[] PROJECTION_ALL = { _ID, KEY, LANG, VALUE};

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_LANGUAGE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGE);
        onCreate(database);
    }

    public static void initialize(Context context) {

        ContentResolver cr = context.getContentResolver();

        /** Language entry 1 **/
        ContentValues contentValues = new ContentValues();

        contentValues.put(LanguageTable.KEY, "key_question1");
        contentValues.put(LanguageTable.LANG, Constants.FR);
        contentValues.put(LanguageTable.VALUE, "Question 1");

        cr.insert(DBContentProvider.LANGUAGE_CONTENT_URI, contentValues);

        /** Language entry 2 **/
        contentValues = new ContentValues();

        contentValues.put(LanguageTable.KEY, "key_question2");
        contentValues.put(LanguageTable.LANG, Constants.FR);
        contentValues.put(LanguageTable.VALUE, "Question 2");

        cr.insert(DBContentProvider.LANGUAGE_CONTENT_URI, contentValues);

    }
}
