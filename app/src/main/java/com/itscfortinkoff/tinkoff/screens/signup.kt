package com.itscfortinkoff.tinkoff.screens

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
import com.itscfortinkoff.tinkoff.api.Retrofit
import com.itscfortinkoff.tinkoff.api.UserApi
import com.itscfortinkoff.tinkoff.objects.Routes
import com.itscfortinkoff.tinkoff.ui.theme.MainYellow
import com.itscfortinkoff.tinkoff.ui.theme.WhiteYellow
import com.itscfortinkoff.tinkoff.view.MainViewModel
import com.itscfortinkoff.tinkoff.view.MyViewModel
import java.security.MessageDigest

@Composable
fun SignUp(
    model: MyViewModel,
    viewModel: MainViewModel,
    navController: NavHostController,
    api: Retrofit
) {
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
                text = stringResource(id = R.string.registration),
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
                    value = model.email,
                    onValueChange = { text ->
                        model.email = text
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
                    value = model.firstName,
                    onValueChange = { text ->
                        model.firstName = text
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.firstname))
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
                    value = model.secondName,
                    onValueChange = { text ->
                        model.secondName = text
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.secondname))
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
                    value = model.patronymic,
                    onValueChange = { text ->
                        model.patronymic = text
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.patronymic))
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
                    value = model.gender,
                    onValueChange = { text ->
                        model.gender = text
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.gender))
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
                    value = model.password,
                    onValueChange = { text ->
                        model.password = text
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
                    model.user.email = model.email
                    model.user.gender = model.gender
                    model.user.name = model.firstName
                    model.user.lastName = model.secondName
                    model.user.password = sha256(model.password)
                    model.user.patronymic = model.patronymic

                    viewModel.insertUser(model.user)

                    val userApi = UserApi(
                        id = 0,
                        name = model.firstName,
                        lastName = model.secondName,
                        gender = model.gender,
                        email = model.email,
                        password = sha256(model.password)
                    )

                    api.postUser(userApi)
                    navController.navigate(Routes.LOGIN_SCREEN)
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
        }
    }
}

fun sha256(input: String): String {
    val bytes = input.toByteArray()
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest(bytes)
    return hashBytes.joinToString("") { "%02x".format(it) }
}