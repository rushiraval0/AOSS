package com.example.engo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="engo.db";
    public static final String TABLE_NAME ="registeruser";
    public static final String TABLE_NAME2 ="registeruserdetails";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="U_email";
    public static final String COL_3 ="U_phone";
    public  static final String COL_4 = "U_pass";
    public  static final String COL_5 = "U_name";
    public  static final String COL_6 = "U_add";
    public  static final String COL_7 = "U_city";
    public  static final String COL_8 = "U_state";
    public  static final String COL_9 = "U_role";
    public  static final String COL_10 = "U_status";
    int id;

    String Uname;
    int rid;


    public DatabaseHelper(Context context) {



        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



        sqLiteDatabase.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY  KEY AUTOINCREMENT,U_name TEXT, U_email TEXT, U_phone TEXT,U_pass TEXT,U_role INTEGER,U_status INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE registeruserdetails (D_ID INTEGER PRIMARY  KEY AUTOINCREMENT,ID INTEGER,U_add TEXT, U_city TEXT, U_state TEXT,U_image BLOB)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(sqLiteDatabase);
    }

    public long addUser(String email, String phone , String pass , String name , String address , String city , String state, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues1 = new ContentValues();
        contentValues.put("U_name",name);
        contentValues.put("U_email",email);
        contentValues.put("U_pass",pass);
        contentValues.put("U_phone",phone);
        contentValues.put("U_role","2");
        contentValues.put("U_status","0");

        contentValues1.put("U_add",address);
        contentValues1.put("U_city", city);
        contentValues1.put("U_state", state);
        contentValues1.put("U_image",image);
        long res = db.insert("registeruser",null,contentValues);
        Cursor cursor1 = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        if(cursor1.moveToLast()){
            //name = cursor.getString(column_index);//to get other values
            id = cursor1.getInt(0);//to get id, 0 is the column index
        }
        contentValues1.put("ID",id);
        db.insert("registeruserdetails",null,contentValues1);


        db.close();
        return  res;
    }

    public boolean checkUser(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();


        if(count>0)
            {
            return  true;
            }
        else
            {

            return  false;
            }



    }


    public List<String> getAllData(String email)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        String[] projection={email};



        List<String> list=new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, //Table to query
                null,    //columns to return
                "U_email=?",        //columns for the WHERE clause
                projection,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null);
        //  cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {

                list.add(cursor.getString(cursor.getColumnIndex("ID")));
                list.add(cursor.getString(cursor.getColumnIndex("U_name")));

            } while (cursor.moveToNext());
        }



        return list;
    }


    public byte[] getImage(String id)
    {
        byte[] image = new byte[0];
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        String[] projection={id};
        String[] columns = { "U_image" };

        Cursor cursor1 = db.query(TABLE_NAME2, //Table to query
                columns,    //columns to return
                "ID=?",        //columns for the WHERE clause
                projection,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null);



        if (cursor1.moveToFirst()) {
            do {

                image = cursor1.getBlob(cursor1.getColumnIndex("U_image"));


            } while (cursor1.moveToNext());
        }

        return image;


    }










}
