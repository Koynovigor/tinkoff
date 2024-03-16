package com.itscfortinkoff.tinkoff.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    var id: Int = 0

    @ColumnInfo(name = "userName")
    var name: String = ""

    @ColumnInfo(name = "userLastName")
    var lastName: String = ""

    @ColumnInfo(name = "userGender")
    var gender: String = ""

    @ColumnInfo(name = "userEmail")
    var email: String = ""

    @ColumnInfo(name = "userPassword")
    var password: String = ""

    @ColumnInfo(name = "userPatronymic")
    var patronymic: String = ""

    constructor()
    constructor(
        userId: Int,
        userName: String,
        userLastName: String,
        userGender: String,
        userEmail: String,
        userPassword: String,
        userPatronymic: String
    ) {
        this.id = userId
        this.name = userName
        this.lastName = userLastName
        this.gender = userGender
        this.email = userEmail
        this.password = userPassword
        this.patronymic = userPatronymic
    }
    constructor(
        userName: String,
        userLastName: String,
        userGender: String,
        userEmail: String,
        userPassword: String,
        userPatronymic: String
    ) {
        this.name = userName
        this.lastName = userLastName
        this.gender = userGender
        this.email = userEmail
        this.password = userPassword
        this.patronymic = userPatronymic
    }
}
