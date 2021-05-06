package com.nghia.sqlite_open_helper.new_room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nghia.sqlite_open_helper.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by nghia.vuong on 06,May,2021
 */
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = App.instance.database!!.userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) { repository.addUser(user) }
    }

    fun getUser(id:Int) {
        viewModelScope.launch(Dispatchers.IO) { repository.getUser(id) }
    }
}