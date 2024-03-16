package com.itscfortinkoff.tinkoff.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.itscfortinkoff.tinkoff.R
import com.itscfortinkoff.tinkoff.api.Find
import com.itscfortinkoff.tinkoff.api.Retrofit
import com.itscfortinkoff.tinkoff.view.MyViewModel

@Composable
fun Search(
    model: MyViewModel,
    api: Retrofit
) {
    LazyColumn {
        items(model.responseFind) { find ->
            UserCard(
                model = model,
                id = find
            )
        }
    }
}

@Composable
fun UserCard(
    model: MyViewModel,
    id: Find
){
    Card {
        Column() {
            MyText(
                one = id.userId.toString(),
                two = id.city.toString()
            )
        }
    }
}