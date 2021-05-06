package com.nghia.sqlite_open_helper

import android.app.Application
import com.nghia.sqlite_open_helper.new_room.RoomDatabaseHelper
import com.nghia.sqlite_open_helper.new_room.User

/**
 * Created by nghia.vuong on 04,May,2021
 */
class App : Application() {

    var database: RoomDatabaseHelper? = null


    companion object {
        lateinit var instance: App private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // When upgrading version, kill original tables by using fallbackToDestructiveMigration()
        database = RoomDatabaseHelper.getInstance(applicationContext)
        /*  Room uses SQLiteOpenHelper, much as you might use it directly.
            SQLiteOpenHelper does not create the database when you create the SQLiteOpenHelper instance. It will do so once you call getReadableDatabase() or getWriteableDatabase().
            From a Room standpoint, that means until you perform some concrete operation, such as invoking a @Dao method that hits the database, your database will not be created.
            */

    }

}