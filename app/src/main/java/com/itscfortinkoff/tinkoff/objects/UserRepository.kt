package com.itscfortinkoff.tinkoff.objects

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertUser(newUser: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.insertUser(newUser)
        }
    }

    fun deleteUser(user: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.deleteUser(user)
        }
    }

    fun updateUser(user: User){
        coroutineScope.launch(Dispatchers.IO) {
            userDao.updateUser(user)
        }
    }
}