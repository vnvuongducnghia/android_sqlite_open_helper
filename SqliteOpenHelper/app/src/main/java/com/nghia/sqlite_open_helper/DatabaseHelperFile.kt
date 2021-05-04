package com.nghia.sqlite_open_helper

import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.nghia.sqlite_open_helper.DatabaseConstant.DB_NAME
import com.nghia.sqlite_open_helper.DatabaseConstant.DB_VERSION
import java.io.*

/**
 * Created by nghia.vuong on 04,May,2021
 */
object DatabaseHelperFile : SQLiteOpenHelper(
    App.instance.applicationContext,
    DB_NAME, null,
    DB_VERSION
) {
    private var databaseFile: File? = null
    private var mCopyFileSqlite = true

    override fun onCreate(db: SQLiteDatabase?) {
        databaseFile = App.instance.applicationContext.getDatabasePath(DB_NAME)
        if (mCopyFileSqlite) {
            copyDatabase()
        }
        println("DatabaseHelperFile.onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        doUpgrade(db)
        println("DatabaseHelperFile.onUpgrade")
    }

    /**
     * called if a database upgrade is needed
     */
    private fun doUpgrade(db: SQLiteDatabase?) {
        // implement the database upgrade here.
        // do something like db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    fun checkDataBase(DB_PATH: String?, DB_NAME: String?): Boolean {
        val checkDB = try {
            SQLiteDatabase.openDatabase(DB_PATH!!, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
            //database does't exist yet.
            return false
        }
        return if (checkDB != null) {
            checkDB.close()
            true
        } else {
            false
        }
    }

    private fun clearData() {
        if (checkDataBase(databaseFile!!.path, DB_NAME)) {
            App.instance.applicationContext.deleteDatabase(DatabaseHelperAssets.DATABASE_NAME)
        }
    }

    private fun copyDatabase() {
        val assetManager: AssetManager = App.instance.applicationContext.resources.assets
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            inputStream = assetManager.open(DB_NAME)
            outputStream = FileOutputStream(databaseFile)
            val buffer = ByteArray(1024)
            var read = 0
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            mCopyFileSqlite = false
        } catch (e: IOException) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                }
            }
        }
    }
}