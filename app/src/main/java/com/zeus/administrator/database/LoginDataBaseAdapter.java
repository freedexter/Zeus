package com.zeus.administrator.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

public class LoginDataBaseAdapter{


    private DataBaseHelper dbHelper;
    private SQLiteDatabase sdb;
    public LoginDataBaseAdapter(Context context){
        dbHelper=DataBaseHelper.getDataBaseHelper(context);
        try {
            dbHelper.createDataBase();
        }catch (IOException e){
            throw new Error("Unable to create database");
        }
        try {

            sdb = dbHelper.openDataBase();

        }catch( IOException e){
            throw new Error("Unable to open database");
        }
    }

    public String login(String username,String password ){
        String ret;

        if( !sdb.isOpen() ){
            sdb=dbHelper.getWritableDatabase();
        }
        sdb.beginTransaction();
        String sql="select * from user where username=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username});
        if(cursor.moveToFirst()==true){
            if( password.equals(cursor.getString(cursor.getColumnIndex("password"))) == false ){
                cursor.close();
                sdb.endTransaction();
                sdb.close();
                ret = "False";
                return ret;
            }
            ret = cursor.getString(cursor.getColumnIndex("stat"));
            Log.v("tag", username+ret);
        }else{
            ret = "10";
            User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setStat(ret);
            this.register(user);
        }
        Log.v("tag", username+ret);
        cursor.close();
        sdb.endTransaction();
        sdb.close();
        return ret;
    }

    public String getRemberUser(){
        String username=null;
        if( !sdb.isOpen() ){
            sdb=dbHelper.getWritableDatabase();
        }
        sdb.beginTransaction();
        String sql="select * from user where substr(stat,2,2)='1'";
        Cursor cursor=sdb.rawQuery(sql, null);
        if(cursor.moveToFirst()==true){
            username =cursor.getString(  cursor.getColumnIndex("username") );
        }

        cursor.close();
        sdb.endTransaction();
        sdb.close();
        return username;
    }
    public String remberUser(String username){
        String sql,string1;
        String stat=null;
        //Object[] arrayOfObject = new Object[2];
        Cursor cursor;
        char[] a;

        if( !sdb.isOpen() ){
            sdb=dbHelper.getWritableDatabase();
        }
        sdb.beginTransaction();
        sql="select * from user where username = '"+username+"'";
        cursor=sdb.rawQuery(sql, null);
        if(cursor.moveToFirst()==true){
            stat =cursor.getString(  cursor.getColumnIndex("stat") );
        }
        string1 = stat.substring( 0,1) + "1";
       // Log.v("tag",stat);
       // Log.v("tag",string1);
        sdb.endTransaction();
        sdb.beginTransaction();
        sql="update user set stat='"+string1+"' where username ='"+username+"'";
        sdb.execSQL(sql);

        sdb.setTransactionSuccessful();
        cursor.close();
        sdb.endTransaction();
        sdb.close();
        return username;
    }

    public String unremberUser(String username){
        String sql,string1;
        String stat=null;
        //Object[] arrayOfObject = new Object[2];
        Cursor cursor;
        char[] a;

        if( !sdb.isOpen() ){
            sdb=dbHelper.getWritableDatabase();
        }
        sdb.beginTransaction();
        sql="select * from user where username = '"+username+"'";
        cursor=sdb.rawQuery(sql, null);
        if(cursor.moveToFirst()==true){
            stat =cursor.getString(  cursor.getColumnIndex("stat") );
        }
        string1 = stat.substring( 0,1) + "0";
      //  Log.v("tag",stat);
       // Log.v("tag",string1);
        sdb.endTransaction();
        sdb.beginTransaction();
        sql="update user set stat='"+string1+"' where username ='"+username+"'";
        sdb.execSQL(sql);
        sdb.setTransactionSuccessful();
        cursor.close();
        sdb.endTransaction();
        sdb.close();
        return username;
    }

    //注册用户
    public boolean register(User user){
        if( !sdb.isOpen() ){
            sdb=dbHelper.getWritableDatabase();
        }
        sdb.beginTransaction();
        String sql="insert into user(username,password,stat) values(?,?,?)";
        Object obj[]={user.getUsername(),user.getPassword(),user.getStat()};
        sdb.execSQL(sql, obj);
        sdb.setTransactionSuccessful();
        sdb.endTransaction();
        sdb.close();
        return true;
    }

}
