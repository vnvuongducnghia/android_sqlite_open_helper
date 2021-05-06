package com.nghia.sqlite_open_helper.new_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nghia.sqlite_open_helper.old.DatabaseConstant.DB_NAME
import com.nghia.sqlite_open_helper.old.DatabaseConstant.DB_VERSION

/**
 * Created by nghia.vuong on 06,May,2021
 */
@Database(entities = [User::class], version = 1)
abstract class RoomDatabaseHelper : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabaseHelper? = null

        fun getInstance(context: Context): RoomDatabaseHelper {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context,
                        RoomDatabaseHelper::class.java,
                        DB_NAME
                    )
                        .addMigrations(MIGRATION_UPDATE_DATA)
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }

        @JvmField
        val MIGRATION_UPDATE_DATA: Migration = object : Migration(1, DB_VERSION) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }
    }
}