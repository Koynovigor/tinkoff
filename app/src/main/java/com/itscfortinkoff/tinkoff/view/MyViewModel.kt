package com.itscfortinkoff.tinkoff.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.itscfortinkoff.tinkoff.api.Find
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
    var status by mutableIntStateOf(1)

    var text by mutableStateOf(TextFieldValue())
    var isDropdownMenuExpanded by mutableStateOf(false)
    val dropdownItems = listOf(
        "Tag1", "Tag2", "Tag3",
        "Tag4", "Tag5", "Tag6",
        "Tag7", "Tag8", "Tag9",
    )

    var user = User(
        userName = "",
        userLastName = "",
        userEmail = "",
        userGender = "",
        userPassword = "",
        userPatronymic = ""
    )

    var userApi = UserApi(
        id = 0,
        name = "",
        lastName = "",
        gender = "",
        email = "",
        password = ""
    )

    var find_id = 0
    var isCity: Boolean = false

    var responseFind: MutableList<Find> = mutableListOf()
}