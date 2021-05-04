package com.nghia.sqlite_open_helper

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        dbManager = DBManager()
//        dbManager.open()
        var database: SQLiteDatabase = DatabaseHelperFile.writableDatabase

    }
}