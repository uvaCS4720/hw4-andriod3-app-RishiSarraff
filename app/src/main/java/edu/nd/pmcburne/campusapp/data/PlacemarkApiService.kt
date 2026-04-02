package edu.nd.pmcburne.campusapp.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private val json = Json { ignoreUnknownKeys = true }

interface PlacemarkApiService{
    @GET("placemarks.json")
    suspend fun getPlacemarks(): List<PlacemarkResponseObject>
}

object PlacemarkApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cs.virginia.edu/~wxt4gm/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val service: PlacemarkApiService by lazy {
        retrofit.create(PlacemarkApiService::class.java)
    }
}