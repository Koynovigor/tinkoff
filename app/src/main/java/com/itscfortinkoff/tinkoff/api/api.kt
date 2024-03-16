package com.itscfortinkoff.tinkoff.api

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.itscfortinkoff.tinkoff.view.MyViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

data class UserApi(
    var id: Int,
    var name: String,
    var lastName: String,
    var gender: String,
    var email: String,
    var password: String
)

data class MyResponse(
    val message: String
)

val url = "http://26.140.205.19:5082"

interface ApiService {
    @GET("/api/users/login")
    fun getUser(@Query("Email") email: String, @Query("Password") password: String): Call<List<UserApi>>

    @GET("/api/users/login")
    fun login(@Query("Email") email: String, @Query("Password") password: String): Call<UserApi>

    @POST("/api/users/create")
    fun createUser(@Body userApi: UserApi): Call<MyResponse>

    @DELETE("/api/users/create")
    fun deleteUser(): Call<Void>
}

class ComposeMain {
    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    fun SeeUser() {

        val api = Retrofit()
        var userApiList: List<UserApi> = mutableListOf()
        val apiUser = retrofit2.Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var name by remember { mutableStateOf("") }
            var age by remember { mutableStateOf("") }
            var elements by remember {
                mutableStateOf(userApiList)
            }
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )
            TextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") }
            )

            Button(onClick = {
                if(name.isNotEmpty() && age.isNotEmpty()) {

                }
            }) {
                Text("Save")
            }
            Button(onClick = {
                userApiList = api.getUsers("", "")
                GlobalScope.launch(Dispatchers.IO) {
                    val response = apiUser.getUser("", "").awaitResponse()
                    if(response.isSuccessful){
                        userApiList = response.body()!!
                        elements = userApiList
                    }
                }

            }) {
                Text("Read")
            }
            Button(onClick = {
                GlobalScope.launch(Dispatchers.IO) {
                    val response = apiUser.deleteUser().awaitResponse()
                    if(response.isSuccessful){
                        elements = emptyList()
                    }
                }
            }) {
                Text("Delete All")
            }


            LazyColumn(

            ) {
                itemsIndexed(elements) { _, item ->
                    PrintUserColumn(item)
                }
            }


        }
    }

    @Composable
    fun PrintUserColumn(item: UserApi){
        Row() {
            Text(
                text = item.name,
                modifier = Modifier
                    .padding(end = 5.dp))
            Text(text = item.name)
        }
    }
}

class Retrofit {
    val model = MyViewModel()
    fun getUsers(email: String, password: String): MutableList<UserApi>{
        val okHttpClient: OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        val userApiList: MutableList<UserApi> = mutableListOf()
        val api= Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.getUser(email, password).enqueue(object : Callback<List<UserApi>> {
            override fun onResponse(
                call: Call<List<UserApi>>,
                response: Response<List<UserApi>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let{
                        for(comm in it){
                            userApiList.add(comm)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<UserApi>>, t: Throwable) {
            }
        })
        return userApiList
    }

    fun login(email: String, password: String){
        val okHttpClient: OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

        val api = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val call = api.login(email, password)
        call.enqueue(object : Callback<UserApi> {
            override fun onResponse(call: Call<UserApi>, response: Response<UserApi>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    model.userApi.id = dataResponse?.id!!
                    model.userApi.name = dataResponse.name
                    model.userApi.lastName = dataResponse.lastName
                    model.userApi.gender = dataResponse.gender
                    model.userApi.email = dataResponse.email
                    model.userApi.password = dataResponse.password
                } else {
                    model.userApi.id = -1
                }
            }
            override fun onFailure(call: Call<UserApi>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun postUser(userApi: UserApi){
        val okHttpClient: OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

        val api = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val call = api.createUser(userApi)
        call.enqueue(object : Callback<MyResponse> {
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                if (response.isSuccessful) {
                    val responseModel = response.body()
                    // Обработка успешного ответа
                    if (responseModel != null) {
                        val gson = Gson()
                        val jsonObject = gson.fromJson(responseModel.message, Map::class.java)
                        Log.d("post", jsonObject.toString())
                    }
                } else {
                    // Обработка неуспешного ответа
                }
            }
            override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                // Обработка ошибки
            }
        })
    }

    object UnsafeOkHttpClient {
        fun getUnsafeOkHttpClient(): OkHttpClient {
            try {
// Create a trust manager that does not validate certificate chains
                val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }
                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                val sslContext: SSLContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

                val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { _, _ -> true }

                val okHttpClient: OkHttpClient = builder.build()
                return okHttpClient
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }


}