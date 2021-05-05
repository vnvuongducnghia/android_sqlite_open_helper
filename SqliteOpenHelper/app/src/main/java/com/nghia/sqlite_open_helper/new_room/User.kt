package com.nghia.sqlite_open_helper.new_room

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by nghia.vuong on 05,May,2021
 */
// Defining your table
@Entity(tableName = "users")
class User(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String?,
    @ColumnInfo(name = "last_name")
    val lastName: String?,
    @Ignore val picture: Bitmap? // ignore fields
)

// ignoreColumns
