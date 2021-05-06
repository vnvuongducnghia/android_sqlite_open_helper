package com.nghia.sqlite_open_helper

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nghia.sqlite_open_helper.new_room.User
import com.nghia.sqlite_open_helper.new_room.UserViewModel
import com.nghia.sqlite_open_helper.old.DBManager
import com.nghia.sqlite_open_helper.old.DatabaseHelperFile

class MainActivity : AppCompatActivity() {

    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        dbManager = DBManager()
//        dbManager.open()
//        var database: SQLiteDatabase = DatabaseHelperFile.writableDatabase
        // Cannot access database on the main thread since it may potentially lock the ui
        val user = User(3, "nghia", "vuong")
        val userViewModel = UserViewModel(application)
        userViewModel.addUser(user)
    }
}