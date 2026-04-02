package edu.nd.pmcburne.campusapp.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

private val json = Json { ignoreUnknownKeys = true }

private val client = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .build()

interface PlacemarkApiService {
    @GET("placemarks.json")
    suspend fun getPlacemarks(): List<PlacemarkResponse>
}

object PlacemarkApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cs.virginia.edu/~wxt4gm/")
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val service: PlacemarkApiService by lazy {
        retrofit.create(PlacemarkApiService::class.java)
    }
}