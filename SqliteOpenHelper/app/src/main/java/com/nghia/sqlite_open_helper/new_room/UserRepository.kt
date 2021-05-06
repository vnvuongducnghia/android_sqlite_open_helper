package com.nghia.sqlite_open_helper.new_room

import androidx.lifecycle.LiveData

/**
 * Created by nghia.vuong on 06,May,2021
 */
class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.getAllData()

    suspend fun addUser(user:User){
        userDao.insertUser(user)
    }

    suspend fun getUser(id: Int): User {
      return  userDao.findUsersByName(id)
    }


}