package com.itscfortinkoff.tinkoff.objects

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>
}

