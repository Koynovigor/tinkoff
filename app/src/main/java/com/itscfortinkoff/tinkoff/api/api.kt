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
import com.google.gson.annotations.SerializedName
import com.itscfortinkoff.tinkoff.screens.sha256
import com.itscfortinkoff.tinkoff.view.MyViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

data class TagsPost(
    var userId: Int,
    var tags: String
)

data class Find(
    var userId: Int,
    var city: Int
)

data class ResponseData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

val url = "http://10.10.110.219:5082"

interface ApiService {
    @GET("/api/users/login")
    fun getUser(
        @Query("Id=") id: Int,
    ): Call<UserApi>

    @GET("/api/users/login")
    fun login(
        @Query("Email") email: String,
        @Query("Password") password: String
    ): Call<ResponseData>

    @GET("api/users/findBestMatchingUsers")
    fun find(
        @Query("userId") userId: Int,
        @Query("isCity") isCity: Boolean
    ): Call<List<Find>>

    @POST("/api/users/create")
    fun createUser(@Body userApi: UserApi): Call<MyResponse>

    @POST("/api/users/addOrUpdateTags")
    fun tags(
        @Body tags: TagsPost
    ): Call<MyResponse>

    @DELETE("/api/users/create")
    fun deleteUser(): Call<Void>

    @Multipart
    @POST("api/upload/image")
    fun uploadImage(@Part file: MultipartBody.Part): Call<Void>

    @GET("/api/user/image/getmyimg")
    fun getUserImage(@Query("userId") userId: Int): Call<ResponseBody>

    companion object {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
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

    fun find(
        model: MyViewModel
    ){
        val apiService = ApiService.create()

        val call = apiService.find(model.find_id, model.isCity)

        call.enqueue(object : Callback<List<Find>> {
            override fun onResponse(call: Call<List<Find>>, response: Response<List<Find>>) {
                if (response.isSuccessful) {
                    for (find in response.body()!!){
                        Log.d("Error","Error: ${find.userId}")
                        model.responseFind.add(find)
                    }
                } else {
                    Log.d("Error","Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Find>>, t: Throwable) {
                Log.d("Error","Request failed: ${t.message}")
            }
        })
    }

    fun getUser(id: Int, model: MyViewModel){
        val apiService = ApiService.create()
        val call = apiService.login(model.email_cur, model.password_cur)

        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        model.userApi.id = responseData.id
                        model.userApi.name = responseData.name
                        model.userApi.lastName = responseData.lastName
                        model.userApi.gender = responseData.gender
                        model.userApi.email = responseData.email
                        model.userApi.password = responseData.password
                        Log.d("print","" +
                                "${responseData.id}     " +
                                "${responseData.name}   " +
                                "${responseData.lastName}   " +
                                "${responseData.gender} " +
                                "${responseData.email}  " +
                                "${responseData.password}   ")
                    }
                } else {
                    Log.d("Error","Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.d("Error","Request failed: ${t.message}")
            }
        })
    }

    fun login(model: MyViewModel) {
        val apiService = ApiService.create()
        val call = apiService.login(model.email_cur, sha256(model.password_cur))

        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        Log.d("Error","" +
                                "${responseData.id}     " +
                                "${responseData.name}   " +
                                "${responseData.lastName}   " +
                                "${responseData.gender} " +
                                "${responseData.email}  " +
                                "${responseData.password}   ")
                        model.userApi.id = responseData.id
                        model.userApi.name = responseData.name
                        model.userApi.lastName = responseData.lastName
                        model.userApi.gender = responseData.gender
                        model.userApi.email = responseData.email
                        model.userApi.password = responseData.password
                    }
                } else {
                    Log.d("Error","Error238: ${response.code()} ${response.message()} ${response.raw()}")
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.d("Error243","Request failed: ${t.message}")
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

    fun tags(tags: TagsPost){
        val okHttpClient: OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

        val api = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val call = api.tags(tags)
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