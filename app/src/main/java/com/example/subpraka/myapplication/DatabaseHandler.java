package com.example.subpraka.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by subpraka on 7/12/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "dexterDb";

    // Contacts table name
    private static final String TABLE_USER = "user";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email_id";
    private static final String KEY_PASSWORD = "password";
    public Boolean result ;
    String uEmail ,uPassword;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " +
                KEY_EMAIL + " TEXT, " + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

                                             //Adding new User

    public long addUser(UserInfo userInfo) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(KEY_ID, userInfo.get_id()); // User Id
        values.put(KEY_NAME, userInfo.get_name()); // User Name
        values.put(KEY_EMAIL, userInfo.get_email()); // User Email
        values.put(KEY_PASSWORD, userInfo.get_password()); // User Password

        // Inserting Row
        long insertedRowID = db.insert(TABLE_USER, null, values);
        db.close();

        return insertedRowID;
    }

    public boolean searchpass(String uEmail , String uPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT email_id , password from " + TABLE_USER +" where "+ KEY_EMAIL
                + " = '"+ uEmail + "' AND " + KEY_PASSWORD + "  = '" + uPassword + "'" , null);

        cursor.moveToFirst();
        return  cursor.getCount() > 0 ;

//
//        int sum = 0;
//        if (cursor != null) {
//            try {
//                if (cursor.moveToFirst()) {
//                    sum = cursor.getInt(0);
//                }
//            } finally {
//                cursor.close();
//            }


//        Cursor cursor = db.rawQuery(query, null);
//        String email_id, newPassword;
//        newPassword = "Not Found";
//        if (cursor.moveToFirst()) {
//
//            do {
//                email_id = cursor.getString(cursor.getColumnIndex("email_id"));
//                newPassword = cursor.getString(cursor.getColumnIndex("password"));
//
//                if (email_id.equals(name)) {
//                    newPassword = cursor.getString(2);
//                    break;
//                }
//            }
//            while (cursor.moveToNext());
//        }


    }

}
