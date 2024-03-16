package com.itscfortinkoff.tinkoff.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.itscfortinkoff.tinkoff.api.UserApi
import com.itscfortinkoff.tinkoff.objects.User

class MyViewModel:ViewModel() {
    var email by mutableStateOf("")
    var firstName by mutableStateOf("")
    var secondName by mutableStateOf("")
    var patronymic by mutableStateOf("")
    var gender by mutableStateOf("")
    var password by mutableStateOf("")

    var email_cur by mutableStateOf("")
    var password_cur by mutableStateOf("")

    var userApiList: List<UserApi> = mutableListOf()
    var elements by mutableStateOf(userApiList)

    var user = User(
        userName = "",
        userLastName = "",
        userEmail = "",
        userGender = "",
        userPassword = "",
        userPatronymic = ""
    )

    val userApi = UserApi(
        id = 0,
        name = "",
        lastName = "",
        gender = "",
        email = "",
        password = ""
    )
}