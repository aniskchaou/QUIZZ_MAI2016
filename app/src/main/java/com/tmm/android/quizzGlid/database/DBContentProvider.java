package com.tmm.android.quizzGlid.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.tmm.android.quizzGlid.database.tables.LanguageTable;
import com.tmm.android.quizzGlid.database.tables.QuestionTable;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by bilel on 08/05/16.
 */
public class DBContentProvider extends ContentProvider {

    // database
    private DBHelper mOpenHelper;

    private static final String AUTHORITY = "com.iit.testproviders.main.provider";

    public static final Uri QUESTION_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + QuestionTable.CONTENT_PATH);
    public static final Uri LANGUAGE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + LanguageTable.CONTENT_PATH);

    private static final int QUESTION_ALL = 10;
    private static final int QUESTION_ID = 11;
   // private static final int LISTS_ALL = 12;
   // private static final int LIST_ID = 13;
   private static final int LANGUAGE_ID = 20;


    private static final UriMatcher TEST_PROVIDER_URI_MATCHER;

    static {
        TEST_PROVIDER_URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        TEST_PROVIDER_URI_MATCHER.addURI(AUTHORITY, QuestionTable.CONTENT_PATH, QUESTION_ALL);
        TEST_PROVIDER_URI_MATCHER.addURI(AUTHORITY, LanguageTable.CONTENT_PATH, LANGUAGE_ID);
        TEST_PROVIDER_URI_MATCHER.addURI(AUTHORITY, QuestionTable.CONTENT_PATH + "/#", QUESTION_ID);
       // TEST_PROVIDER_URI_MATCHER.addURI(AUTHORITY, ListsTable.CONTENT_PATH,
              //  LISTS_ALL);
        //TEST_PROVIDER_URI_MATCHER.addURI(AUTHORITY, ListsTable.CONTENT_PATH
               // + "/#", LIST_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
        int rowsDeleted = 0;
        String id;
        switch (TEST_PROVIDER_URI_MATCHER.match(uri)) {
            case QUESTION_ALL:
                rowsDeleted = sqlDB.delete(QuestionTable.TABLE_QUESTION, selection,
                        selectionArgs);
                break;
            case QUESTION_ID:
                //retrieve the record id to delete
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(QuestionTable.TABLE_QUESTION,
                            QuestionTable._ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(QuestionTable.TABLE_QUESTION,
                            QuestionTable._ID + "=" + id + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        switch (TEST_PROVIDER_URI_MATCHER.match(uri)) {
            case QUESTION_ALL:
                return QuestionTable.CONTENT_TYPE;
            case LANGUAGE_ID:
                return LanguageTable.CONTENT_TYPE;
            case QUESTION_ID:
                return QuestionTable.CONTENT_ITEM_TYPE;
          /*  case LISTS_ALL:
                return ListsTable.CONTENT_TYPE;
            case LIST_ID:
                return ListsTable.CONTENT_ITEM_TYPE;*/
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        Uri itemUri = null;

        long id = 0;

        switch (TEST_PROVIDER_URI_MATCHER.match(uri)) {
            case QUESTION_ALL:

                id = database.insert(QuestionTable.TABLE_QUESTION, null, values);
                if (id > 0) {
                    // notify all listeners of changes and return itemUri:
                    itemUri = ContentUris.withAppendedId(uri, id);
                    getContext().getContentResolver().notifyChange(itemUri, null);
                } else {
                    // something went wrong:
                    throw new SQLException("Problem while inserting into "
                            + QuestionTable.TABLE_QUESTION + ", uri: " + uri);
                }
                break;
            case LANGUAGE_ID:
                id = database.insert(LanguageTable.TABLE_LANGUAGE, null, values);
                if (id > 0) {
                    // notify all listeners of changes and return itemUri:
                    itemUri = ContentUris.withAppendedId(uri, id);
                    getContext().getContentResolver().notifyChange(itemUri, null);
                } else {
                    // something went wrong:
                    throw new SQLException("Problem while inserting into "
                            + LanguageTable.TABLE_LANGUAGE + ", uri: " + uri);
                }
                break;
            /*case LISTS_ALL:

                id = database.insert(ListsTable.TABLE_LISTS, null, values);
                if (id > 0) {
                    // notify all listeners of changes and return itemUri:
                    itemUri = ContentUris.withAppendedId(uri, id);
                    getContext().getContentResolver().notifyChange(itemUri, null);
                } else {
                    // something went wrong:
                    throw new SQLException("Problem while inserting into "
                            + ListsTable.TABLE_LISTS + ", uri: " + uri);
                }
                break;*/
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return itemUri;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new DBHelper(getContext());
        return true;
    }

    /// selection: id=?"
    // selectionArgs: new String {'5',...}
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.v("iit", "ContentProvider query method: CallingPackage = " + getCallingPackage());
        } else {
            //TODO

        }

        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (TEST_PROVIDER_URI_MATCHER.match(uri)) {
            case QUESTION_ALL:
                // Check if the caller has requested a column which does not
                // exists
                checkRecordsTableColumns(projection);
                // Set the table
                queryBuilder.setTables(QuestionTable.TABLE_QUESTION);
                break;
            case LANGUAGE_ID:
                // Check if the caller has requested a column which does not
                // exists
                checkRecordsTableColumns(projection);
                // Set the table
                queryBuilder.setTables(LanguageTable.TABLE_LANGUAGE);
                break;
            case QUESTION_ID:
                // Check if the caller has requested a column which does not
                // exists
                checkRecordsTableColumns(projection);
                // Set the table
                queryBuilder.setTables(QuestionTable.TABLE_QUESTION);
                // Adding the ID to the original query
                queryBuilder.appendWhere(QuestionTable._ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        int rowsUpdated = 0;
        String id;
        switch (TEST_PROVIDER_URI_MATCHER.match(uri)) {
            case QUESTION_ID:

                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = database.update(QuestionTable.TABLE_QUESTION,
                            values, QuestionTable._ID + "=" + id, null);
                } else {
                    rowsUpdated = database.update(QuestionTable.TABLE_QUESTION,
                            values, QuestionTable._ID + "=" + id + " and "
                                    + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkRecordsTableColumns(String[] projection) {

        if (projection != null) {

            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(QuestionTable.PROJECTION_ALL));
            // Check if all columns which are requested are available
            /*if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }*/
        }
    }
}