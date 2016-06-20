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

public class QuestionTable implements BaseColumns {

    public static final int TYPE_RADIO = 1;
    public static final int TYPE_CHECKBOX = 2;

    // Records database table
    public static final String TABLE_QUESTION = "question";

    // table records fields
    public static final String Question = "question";
    public static final String REPONSE = "reponse";
    public static final String PROP1 = "proposition1";
    public static final String PROP2 = "proposition2";
    public static final String PROP3 = "proposition3";
    public static final String DIFF = "diff";
    public static final String TYPE = "type";
    public static final String IMAGE = "image";

    // records table creation statement
    private static final String CREATE_LISTS_TABLE = "CREATE TABLE "
            + TABLE_QUESTION + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Question
            + " TEXT NOT NULL, " + REPONSE
            + " TEXT NOT NULL, " + PROP1
            + " TEXT NOT NULL, " + PROP2
            + " TEXT NOT NULL, " + PROP3
            + " TEXT NOT NULL, " + DIFF
            + " INTEGER ," + TYPE
            + " INTEGER ," + IMAGE
            + " BLOB );" ;

    // info for content provider
    public static final String CONTENT_PATH = "question";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.testprovider.question";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.testprovider.question";

    public static final String[] PROJECTION_ALL = { _ID, Question, REPONSE, PROP1, PROP2, PROP3, DIFF, TYPE, IMAGE};

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_LISTS_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        onCreate(database);
    }

    public static void initialize(Context context) {

        ContentResolver cr = context.getContentResolver();

        /** Question 1 **/
        ContentValues contentValues = new ContentValues();

        contentValues.put(QuestionTable.Question, "key_question1");
        contentValues.put(QuestionTable.REPONSE, "key_prop12");

        contentValues.put(QuestionTable.PROP1, "key_prop11");
        contentValues.put(QuestionTable.PROP2, "key_prop12");
        contentValues.put(QuestionTable.PROP3, "key_prop13");

        contentValues.put(QuestionTable.DIFF, Constants.EASY);
        contentValues.put(QuestionTable.TYPE, TYPE_RADIO);
        contentValues.put(QuestionTable.IMAGE, Utility.drawableToByteArray(context, R.drawable.fr));

        cr.insert(
                DBContentProvider.QUESTION_CONTENT_URI,
                contentValues);


        /** Question 2 **/
        contentValues = new ContentValues();

        contentValues.put(QuestionTable.Question, "key_question2");
        contentValues.put(QuestionTable.REPONSE, "key_prop21,key_prop22");

        contentValues.put(QuestionTable.PROP1, "key_prop21");
        contentValues.put(QuestionTable.PROP2, "key_prop22");
        contentValues.put(QuestionTable.PROP3, "key_prop23");

        contentValues.put(QuestionTable.DIFF, Constants.EASY);
        contentValues.put(QuestionTable.TYPE, TYPE_CHECKBOX);
        contentValues.put(QuestionTable.IMAGE, Utility.drawableToByteArray(context, R.drawable.fr));

        cr.insert(
                DBContentProvider.QUESTION_CONTENT_URI,
                contentValues);

    }
}
