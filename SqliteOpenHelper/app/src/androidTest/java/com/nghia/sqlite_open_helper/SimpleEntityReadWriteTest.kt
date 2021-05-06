package com.nghia.sqlite_open_helper

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nghia.sqlite_open_helper.new_room.User
import com.nghia.sqlite_open_helper.new_room.UserViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by nghia.vuong on 06,May,2021
 */

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var userViewModel: UserViewModel

    @Before
    fun createDb() {
        userViewModel = UserViewModel(App.instance)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        App.instance.database!!.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val user = User(3, "nghia", "vuong")
        userViewModel.addUser(user)
        val userFound = userViewModel.getUser(3)
//        assertThat(userFound, equalTo(user.firstName))
    }
}