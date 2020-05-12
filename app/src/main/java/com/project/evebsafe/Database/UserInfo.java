package com.project.evebsafe.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserInfo extends SQLiteOpenHelper {
    public static final String databasename="Mydatabase.db";
    public static final String tablename="Mytable";
    public static final String COL_1="Name";
    public static final String COL_2="Phone_Number";
    public static final String COL_3="Address";
    public static final String COL_4="Picture";
    public static final String COL_5="Occupation";
    public static final String COL_6="Gender";




    public UserInfo(@Nullable Context context) {
        super(context, databasename, null, 1);
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+tablename+" (Name TEXT,Phone_Number TEXT,Address TEXT,Picture TEXT,Occupation TEXT,Gender TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tablename);

    }
    public boolean insert(String name,String number,String address,String picture,String occupation,String gender)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,number);
        contentValues.put(COL_3,address);
        contentValues.put(COL_4,picture);
        contentValues.put(COL_5,occupation);
        contentValues.put(COL_6,gender);
        long result=db.insert(tablename,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    public ArrayList<String> getIndividualInfo(String number)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<String> arrayList=new ArrayList<>();
        String SQL ="SELECT FROM "+tablename+" WHERE Phone_Number='"+number+"'";
        Cursor cursor=db.rawQuery(SQL,null);
        while (cursor.moveToNext())
        {
          arrayList.add(cursor.getString(0));
            arrayList.add(cursor.getString(1));
            arrayList.add(cursor.getString(2));
            arrayList.add(cursor.getString(3));
            arrayList.add(cursor.getString(4));
            arrayList.add(cursor.getString(5));


        }


        return arrayList;
    }
    public ArrayList<ArrayList<String>>allInfo(){
        SQLiteDatabase db=this.getWritableDatabase();
        String SQL ="SELECT Name,Phone_Number,Picture FROM "+tablename;
        Cursor cursor=db.rawQuery(SQL,null);
        ArrayList<String>Name,Number,Profilepic;
        ArrayList<ArrayList<String>>object;
        Name= new ArrayList<>();
        Number=new ArrayList<>();
        Profilepic=new ArrayList<>();
        object=new ArrayList<>();
        while (cursor.moveToNext()) {
            Name.add(cursor.getString(0));
            Number.add(cursor.getString(1));
            Profilepic.add(cursor.getString(2));
        }
        object.add(Name);
        object.add(Number);
        object.add(Profilepic);



     return object;
    }
    public ArrayList <String>allNumber(){
        SQLiteDatabase db=this.getWritableDatabase();
        String SQL ="SELECT Phone_Number FROM "+tablename;
        Cursor cursor=db.rawQuery(SQL,null);
        ArrayList<String>Number;

        Number=new ArrayList<>();


        while (cursor.moveToNext()) {

            Number.add(cursor.getString(0));

        }

       return Number;
    }


    public void DeleteSingleUser(String number)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        String SQL ="DELETE FROM "+tablename+" WHERE Phone_Number='"+number+"'";
        db.execSQL(SQL);

    }
}
