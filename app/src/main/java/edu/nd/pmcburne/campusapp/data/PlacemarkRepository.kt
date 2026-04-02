package edu.nd.pmcburne.campusapp.data

import kotlinx.coroutines.flow.Flow

class PlacemarkRepository(private val dao: PlacemarkDao){
    val allPlacemarks: Flow<List<PlacemarkEntity>> = dao.getAllPlacemarks()

    suspend fun syncFromApi() {
        android.util.Log.d("Repository", "About to call API")
        val remote = PlacemarkApi.service.getPlacemarks()
        android.util.Log.d("Repository", "Fetched ${remote.size} placemarks")
        dao.insertAll(remote.map { it.toEntity() })
    }
}

