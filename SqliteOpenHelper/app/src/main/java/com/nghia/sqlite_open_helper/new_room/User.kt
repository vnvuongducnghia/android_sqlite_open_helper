package com.nghia.sqlite_open_helper.new_room

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by nghia.vuong on 05,May,2021
 */

// Defining your table
@Entity(tableName = "user")
class User(
    @PrimaryKey
    @ColumnInfo(name = "id_user")
    @NonNull
    var id: Int,
    @ColumnInfo(name = "first_name")
    var firstName: String?,
    @ColumnInfo(name = "last_name")
    var lastName: String?,
) {
    @Ignore
    var picture: Bitmap? = null
}

// Access data using DAO
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user")
    fun getAllData(): LiveData<List<User>>

    @Query("SELECT *  FROM user WHERE id_user like :id")
    fun findUsersByName(id: Int): User
}


