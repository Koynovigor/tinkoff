package com.itscfortinkoff.tinkoff.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.itscfortinkoff.tinkoff.objects.User

class MyViewModel:ViewModel() {
    var email by mutableStateOf("")
    var firstName by mutableStateOf("")
    var secondName by mutableStateOf("")
    var patronymic by mutableStateOf("")
    var gender by mutableStateOf("")
    var password by mutableStateOf("")

    var user = User(
        userId = 0,
        userName = "",
        userLastName = "",
        userEmail = "",
        userGender = "",
        userPassword = "",
        userPatronymic = ""
    )
}