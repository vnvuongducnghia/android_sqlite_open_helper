package com.nghia.sqlite_open_helper.old

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.nghia.sqlite_open_helper.old.DatabaseConstant.DESC
import com.nghia.sqlite_open_helper.old.DatabaseConstant.SUBJECT
import com.nghia.sqlite_open_helper.old.DatabaseConstant.TABLE_NAME
import com.nghia.sqlite_open_helper.old.DatabaseConstant._ID


/**
 * Created by nghia.vuong on 04,May,2021
 */
class DBManager {

    private lateinit var database: SQLiteDatabase

    fun open(): DBManager {
        database = DatabaseHelper.writableDatabase
        return this
    }

    fun close() {
        DatabaseHelper.close()
    }

    fun insert(name: String?, desc: String?) {
        val contentValue = ContentValues()
        contentValue.put(SUBJECT, name)
        contentValue.put(DESC, desc)
        database.insert(TABLE_NAME, null, contentValue)
    }

    fun fetch(): Cursor? {
        val columns = arrayOf(_ID, SUBJECT, DESC)
        val cursor: Cursor? = database.query(TABLE_NAME, columns, null, null, null, null, null)
        cursor?.moveToFirst()
        return cursor
    }

    fun update(_id: Long, name: String?, desc: String?): Int {
        val contentValues = ContentValues()
        contentValues.put(SUBJECT, name)
        contentValues.put(DESC, desc)
        return database.update(TABLE_NAME, contentValues, "$_ID = $_id", null)
    }

    fun delete(_id: Long) {
        database.delete(TABLE_NAME, "$_ID = $_id", null)
    }
}