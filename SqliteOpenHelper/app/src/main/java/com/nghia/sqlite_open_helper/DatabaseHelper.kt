package com.nghia.sqlite_open_helper

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.nghia.sqlite_open_helper.DatabaseConstant.DB_NAME
import com.nghia.sqlite_open_helper.DatabaseConstant.DB_VERSION
import com.nghia.sqlite_open_helper.DatabaseConstant.DESC
import com.nghia.sqlite_open_helper.DatabaseConstant.SUBJECT
import com.nghia.sqlite_open_helper.DatabaseConstant.TABLE_NAME
import com.nghia.sqlite_open_helper.DatabaseConstant._ID

/**
 * Created by nghia.vuong on 04,May,2021
 */


object DatabaseHelper :
    SQLiteOpenHelper(App.instance.applicationContext, DB_NAME, null, DB_VERSION) {

    // Creating table query
    private val CREATE_TABLE: String =
        ("create table ${TABLE_NAME} (${_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${SUBJECT} TEXT NOT NULL, $DESC TEXT);")


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_TABLE)
        println("DatabaseHelper.onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
        println("DatabaseHelper.onUpgrade")
    }

}