package com.itscfortinkoff.tinkoff

import android.app.Application
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.itscfortinkoff.tinkoff.api.ApiService
import com.itscfortinkoff.tinkoff.api.Retrofit
import com.itscfortinkoff.tinkoff.api.UserApi
import com.itscfortinkoff.tinkoff.api.url
import com.itscfortinkoff.tinkoff.objects.Routes
import com.itscfortinkoff.tinkoff.screens.Choice
import com.itscfortinkoff.tinkoff.screens.LogIn
import com.itscfortinkoff.tinkoff.screens.Main
import com.itscfortinkoff.tinkoff.screens.SignUp
import com.itscfortinkoff.tinkoff.screens.Start
import com.itscfortinkoff.tinkoff.ui.theme.MyAppTheme
import com.itscfortinkoff.tinkoff.view.MainViewModel
import com.itscfortinkoff.tinkoff.view.MainViewModelFactory
import com.itscfortinkoff.tinkoff.view.MyViewModel
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    private val model = MyViewModel()
    private lateinit var viewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                val api = Retrofit()
                val apiUser = retrofit2.Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)

                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                val navController = rememberNavController()

                val owner = LocalViewModelStoreOwner.current
                owner?.let {
                    viewModel = viewModel(
                        it,
                        "MainViewModel",
                        MainViewModelFactory(
                            LocalContext.current.applicationContext
                                    as Application
                        )
                    )
                }
                MyAppTheme {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.START_SCREEN
                    ) {
                        composable(Routes.START_SCREEN) {
                            Start(
                                navController = navController
                            )
                        }
                        composable(Routes.CHOISE_SCREEN) {
                            Choice(
                                navController = navController
                            )
                        }
                        composable(Routes.SIGNUP_SCREEN) {
                            SignUp(
                                model = model,
                                viewModel = viewModel,
                                navController = navController,
                                api = api,
                            )
                        }
                        composable(Routes.LOGIN_SCREEN) {
                            LogIn(
                                model = model,
                                viewModel = viewModel,
                                navController = navController,
                                api = api,
                                apiUser = apiUser
                            )
                        }
                        composable(Routes.MAIN_SCREEN) {
                            Main(
                                model = model,
                            )
                        }
                    }
                }

            }
        }
    }
}