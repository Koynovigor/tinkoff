package com.itscfortinkoff.tinkoff.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.itscfortinkoff.tinkoff.view.MyViewModel

@Composable
fun Main(
    model: MyViewModel,
){
    Scaffold {
        Text(
            text = model.userApi.id.toString() + "\n" +
                    model.userApi.name + "\n" +
                    model.userApi.lastName + "\n" +
                    model.userApi.gender + "\n" +
                    model.userApi.email + "\n" +
                    model.userApi.password,
            modifier = Modifier.padding(it)
        )
    }
}