package com.itscfortinkoff.tinkoff.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.itscfortinkoff.tinkoff.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


@Composable
fun GalleryButton(onImageSelected: (Uri) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { result ->
        if (result != null) {
            onImageSelected(result)
        }
    }

    Button(
        onClick = {
            launcher.launch("image/*")
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Open Gallery")
    }
}

@Composable
fun UploadButton(imageUri: Uri?, service: ApiService) {
    val context = LocalContext.current
    Button(
        onClick = {
            if (imageUri != null) {
                uploadImage(imageUri = imageUri, service = service, context = context)
            }
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Upload Image")
    }
}

fun uploadImage(imageUri: Uri?, service: ApiService, context: Context) {
    if (imageUri != null) {
        val filePath = getRealPathFromUri(imageUri, context)
        Log.d("UploadImage", "File path: $filePath") // Логируем путь к файлу
        val file = File(filePath)
        if (file.exists()) {
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            val call = service.uploadImage(body)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    // Обработка успешного ответа
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Обработка ошибки
                }
            })
        } else {
            Log.e("UploadImage", "File does not exist at path: $filePath") // Логируем, если файл не существует
        }
    }
}


// Функция для получения пути файла из URI
fun getRealPathFromUri(uri: Uri, context: Context): String? {
    var filePath: String? = null
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            filePath = it.getString(columnIndex)
        }
    }
    return filePath
}

@Composable
fun LoadImageScreen(service: ApiService) { // Передаем ApiService в качестве параметра
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                loadImageFromServer(service, userId = 31) { bitmap ->
                    imageBitmap = bitmap?.asImageBitmap()
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Load Image from Server")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap!!,
                contentDescription = "Loaded Image"
            )
        }
    }
}

fun loadImageFromServer(service: ApiService, userId: Int, completion: (Bitmap?) -> Unit) {
    val call = service.getUserImage(userId)

    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val imageBytes = response.body()?.bytes()
                if (imageBytes != null) {
                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    completion(bitmap)
                } else {
                    Log.e("loadImageFromServer", "Image bytes are null")
                }
            } else {
                Log.e("loadImageFromServer", "Request not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("loadImageFromServer", "Request failed: ${t.message}")
        }
    })
}