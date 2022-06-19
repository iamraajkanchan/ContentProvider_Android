package com.chinkyfamily.contentprovider.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(private val context : Context , private val factory : SQLiteDatabase.CursorFactory) :
    SQLiteOpenHelper(context , DBConstants.DB_NAME , factory , DBConstants.DB_VERSION)
{

    /**
     * onConfigure callback method of the
     * */
    override fun onConfigure(db : SQLiteDatabase?)
    {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db : SQLiteDatabase?)
    {
        db?.execSQL(DBConstants.CREATE_QUERY)
    }

    override fun onUpgrade(db : SQLiteDatabase? , oldVersion : Int , newVersion : Int)
    {

    }
}