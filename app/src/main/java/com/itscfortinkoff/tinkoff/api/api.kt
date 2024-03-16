package com.itscfortinkoff.tinkoff.api

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
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

data class UserApi(
    val name: String,
    val lastName: String,
    val gender: String,
    val email: String,
    val password: String
)

data class MyResponse(
    val message: String
)

interface ApiService {
    @GET("/api/users/create")
    fun getUser(): Call<List<UserApi>>

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
            .baseUrl("http://26.140.205.19:5082")
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
                    val userApi = UserApi("qwe","123","rty","4","5")
                    api.postUser(userApi)
                }
            }) {
                Text("Save")
            }
            Button(onClick = {
                userApiList = api.getUsers()
                GlobalScope.launch(Dispatchers.IO) {
                    val response = apiUser.getUser().awaitResponse()
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
    fun getUsers(): MutableList<UserApi>{
        val okHttpClient: OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        val userApiList: MutableList<UserApi> = mutableListOf()
        val api= Retrofit.Builder()
            .baseUrl("http://26.140.205.19:5082")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.getUser().enqueue(object : Callback<List<UserApi>> {
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

    fun postUser(userApi: UserApi){
        val okHttpClient: OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

        val api= Retrofit.Builder()
            .baseUrl("http://26.140.205.19:5082")
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
                        // Делаем что-то с данными из responseModel
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