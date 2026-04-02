package edu.nd.pmcburne.campusapp.data

import kotlinx.coroutines.flow.Flow

class PlacemarkRepository(private val dao: PlacemarkDao){
    val allPlacemarks: Flow<List<PlacemarkEntity>> = dao.getAllPlacemarks()

    suspend fun syncFromApi() {
        val remote = PlacemarkApi.service.getPlacemarks()
        dao.insertAll(remote.map {it.toEntity()})
    }
}

