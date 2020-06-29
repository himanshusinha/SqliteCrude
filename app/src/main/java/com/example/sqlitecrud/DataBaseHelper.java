package com.example.sqlitecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper
{
    public static final String db_name="emp.db";
    public static final String table_name="emp_table";

    public String col1="ID";
    public String col2="NAME";
    public String col3="PHNO";

    public DataBaseHelper(@Nullable Context context)
    {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+table_name+"(ID INTEGER PRIMARY KEY ,NAME TEXT,PHNO INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(String name,String phone)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col2,name);
        contentValues.put(col3,phone);
        long result=db.insert(table_name,null,contentValues);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return  true;
        }
    }

    public Cursor getAllData()
    {
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor res=database.rawQuery("select * from "+table_name,null);
        return res;
    }

    public  Integer deleteData(String id)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete(table_name,"ID=?",new String[] {id});
    }

    public boolean updateData(String id,String name)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1,id);
        contentValues.put(col2,name);
        // contentValues.put(col3,phone);

        sqLiteDatabase.update(table_name,contentValues,"ID=?",new String[] {id});
        return true;
    }
}
