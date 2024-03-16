package com.itscfortinkoff.tinkoff.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.itscfortinkoff.tinkoff.R
import com.itscfortinkoff.tinkoff.objects.Routes
import com.itscfortinkoff.tinkoff.ui.theme.Gray80
import com.itscfortinkoff.tinkoff.ui.theme.MainBlack
import com.itscfortinkoff.tinkoff.ui.theme.MainYellow
import com.itscfortinkoff.tinkoff.ui.theme.WhiteYellow


//Фразы на начальном экране
val phrases = mutableListOf(
    R.string.phrasestart1,
    R.string.phrasestart2,
    R.string.phrasestart3,
    R.string.phrasestart4,
    )


@Composable
fun Start(
    navController: NavHostController
) {
    Scaffold(
        containerColor = MainYellow
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 20.dp + it.calculateTopPadding(),
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                )
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(id = R.string.welcom),
                fontWeight = FontWeight(700),
                style = TextStyle(
                    fontSize = 36.sp,
                    lineHeight = 48.sp,
                ),
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(phrases) { id ->
                    MyCard(id)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                              navController.navigate(Routes.CHOISE_SCREEN)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MainBlack
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.run),
                        fontSize = 20.sp,
                        fontWeight = FontWeight(400),
                    )
                }
            }

        }
    }
}

@Composable
fun MyCard(id: Int) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WhiteYellow,
            disabledContainerColor = WhiteYellow,
            contentColor = MainBlack,
            disabledContentColor = MainBlack
        ),
        modifier = Modifier
            .padding(
                vertical = 8.dp,
                horizontal = 0.dp
            )
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = id),
            modifier = Modifier.padding(15.dp),
            fontSize = 20.sp,
            color = MainBlack
        )
    }
}

@Composable
fun Choice(
    navController: NavHostController
){
    Scaffold{
        Column(
            modifier = Modifier
                .padding(
                    top = 20.dp + it.calculateTopPadding(),
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.welcom),
                fontWeight = FontWeight(700),
                style = TextStyle(
                    fontSize = 36.sp,
                    lineHeight = 48.sp,
                ),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navController.navigate(Routes.SIGNUP_SCREEN)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainYellow,
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.signup),
                        fontSize = 20.sp,
                        fontWeight = FontWeight(400),
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(Routes.LOGIN_SCREEN)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Gray80
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                    )
                }
            }
        }
    }
}