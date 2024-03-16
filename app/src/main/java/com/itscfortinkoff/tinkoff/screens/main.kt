package com.itscfortinkoff.tinkoff.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.itscfortinkoff.tinkoff.R
import com.itscfortinkoff.tinkoff.api.ApiService
import com.itscfortinkoff.tinkoff.api.Retrofit
import com.itscfortinkoff.tinkoff.api.TagsPost
import com.itscfortinkoff.tinkoff.api.UserApi
import com.itscfortinkoff.tinkoff.objects.Routes
import com.itscfortinkoff.tinkoff.ui.theme.MainYellow
import com.itscfortinkoff.tinkoff.view.MyViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun Main(
    model: MyViewModel,
    api: Retrofit,
    apiUser: ApiService,
    navController: NavHostController,
){
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "home",
                        tint = Color.Gray
                    )
                }
                IconButton(
                    onClick = {
                        model.find_id = model.userApi.id
                        model.isCity = true
                        GlobalScope.launch(Dispatchers.IO) {
                            val response = apiUser.find(model.find_id, model.isCity).awaitResponse()
                            if(response.isSuccessful){
                                api.find(model)
                            }
                        }
                        navController.navigate(Routes.SEARCH_SCREEN)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "search",
                        tint = Color.Gray
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "email",
                        tint = Color.Gray
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "home",
                        tint = MainYellow
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = 20.dp + it.calculateTopPadding(),
                    horizontal = 20.dp
                ),
            verticalArrangement = Arrangement.Top,

        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 25.dp),
                text = stringResource(id = R.string.main),
                fontWeight = FontWeight(500),
                style = TextStyle(
                    fontSize = 36.sp,
                    lineHeight = 48.sp,
                ),
                textAlign = TextAlign.Center
            )
            MyText(
                one = stringResource(id = R.string.email),
                two = model.userApi.email
            )
            MyText(
                one = stringResource(id = R.string.firstname),
                two = model.userApi.name
            )
            MyText(
                one = stringResource(id = R.string.secondname),
                two = model.userApi.lastName
            )
            MyText(
                one = stringResource(id = R.string.gender),
                two = model.userApi.gender
            )
            Tegs(
                model = model,
                api = api
            )
        }
    }
}

@Composable
fun MyText(
    one: String,
    two: String
){
    Text(
        modifier = Modifier
            .padding(
                vertical = 10.dp,
            ),
        text = "$one    $two",
        color = Color.Gray
    )
}

@Composable
fun Tegs(
    model: MyViewModel,
    api: Retrofit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                onClick = {
                    model.isDropdownMenuExpanded = !model.isDropdownMenuExpanded
                }
            )
            .fillMaxWidth()
    ) {
        MyText(
            one = stringResource(id = R.string.about),
            two = ""
        )
        Column {
            BasicTextField(
                value = model.text,
                onValueChange = {
                    model.text = it
                },
                textStyle = LocalTextStyle.current.copy(color = Color.Gray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                readOnly = true
            )
            DropdownMenu(
                expanded = model.isDropdownMenuExpanded,
                onDismissRequest = { model.isDropdownMenuExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                model.dropdownItems.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            if (model.text.text.isEmpty()) {
                                model.text = TextFieldValue(model.text.text + item)
                            } else {
                                model.text = TextFieldValue(model.text.text + "," + item)
                            }
                            model.isDropdownMenuExpanded = false
                        },
                        text = {
                            Text(text = item)
                        }
                    )
                }
            }
        }
    }
    Button(
        onClick = {
            val tags = TagsPost(
                userId = model.userApi.id,
                tags = model.text.text
            )
            api.tags(tags)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MainYellow,
            contentColor = Color.White
        ),
    ) {
        Text(
            text = stringResource(id = R.string.save),
            fontSize = 20.sp,
            fontWeight = FontWeight(400)
        )
    }
}