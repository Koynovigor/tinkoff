package com.itscfortinkoff.tinkoff.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.itscfortinkoff.tinkoff.api.ApiService
import com.itscfortinkoff.tinkoff.api.Retrofit
import com.itscfortinkoff.tinkoff.api.UserApi
import com.itscfortinkoff.tinkoff.objects.Routes
import com.itscfortinkoff.tinkoff.ui.theme.MainYellow
import com.itscfortinkoff.tinkoff.ui.theme.WhiteYellow
import com.itscfortinkoff.tinkoff.view.MainViewModel
import com.itscfortinkoff.tinkoff.view.MyViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun LogIn(
    model: MyViewModel,
    viewModel: MainViewModel,
    navController: NavHostController,
    api: Retrofit,
    apiUser: ApiService
) {

    var flag by remember {
        mutableStateOf(false)
    }

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
                text = stringResource(id = R.string.login),
                fontWeight = FontWeight(700),
                style = TextStyle(
                    fontSize = 36.sp,
                    lineHeight = 48.sp,
                ),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    value = model.email_cur,
                    onValueChange = { text ->
                        model.email_cur = text
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.email))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = WhiteYellow,
                        unfocusedContainerColor = WhiteYellow,
                        disabledContainerColor = WhiteYellow,
                        errorContainerColor = WhiteYellow,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                        disabledPlaceholderColor = Color.Gray,
                        errorPlaceholderColor = Color.Gray,
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                TextField(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    value = model.password_cur,
                    onValueChange = { text ->
                        model.password_cur = text
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.password))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = WhiteYellow,
                        unfocusedContainerColor = WhiteYellow,
                        disabledContainerColor = WhiteYellow,
                        errorContainerColor = WhiteYellow,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                        disabledPlaceholderColor = Color.Gray,
                        errorPlaceholderColor = Color.Gray,
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }
            Button(
                onClick = {
                    GlobalScope.launch(Dispatchers.IO) {
                        val response = apiUser.login(model.email_cur, sha256(model.password_cur)).awaitResponse()
                        if(response.isSuccessful){
                            api.login(model)
                        }
                    }
                    navController.navigate(Routes.MAIN_SCREEN)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainYellow,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                )
            }
        }
    }
}