package com.itscfortinkoff.tinkoff.view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itscfortinkoff.tinkoff.objects.User
import com.itscfortinkoff.tinkoff.objects.UserRepository
import com.itscfortinkoff.tinkoff.objects.UserRoomDatabase

class MainViewModel(application: Application) : ViewModel() {
    val allUser: LiveData<List<User>>

    private val repository: UserRepository

    init {
        val userDb = UserRoomDatabase.getInstance(application)
        val userDao = userDb.userDao()

        repository = UserRepository(userDao)
        allUser = repository.allUsers
    }

    fun insertUser(user: User) {
        repository.insertUser(user)
    }

    fun updateUser(user: User) {
        repository.updateUser(user)
    }

    fun deleteUser(user: User) {
        repository.deleteUser(user)
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}
