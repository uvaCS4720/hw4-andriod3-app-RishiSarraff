package edu.nd.pmcburne.campusapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.nd.pmcburne.campusapp.data.AppDatabase
import edu.nd.pmcburne.campusapp.data.PlacemarkEntity
import edu.nd.pmcburne.campusapp.data.PlacemarkRepository
import edu.nd.pmcburne.campusapp.data.tagList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapViewModel(application: Application): AndroidViewModel(application){
    private val repository = PlacemarkRepository(
        AppDatabase.getInstance(application).placemarkDao()
    )

    private val allPlacemarks = repository.allPlacemarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val selectedTag = MutableStateFlow("core")

    val allTags: StateFlow<List<String>> = allPlacemarks
        .combine(MutableStateFlow(Unit)) { places, _ ->
            places.flatMap {it.tagList()}.distinct().sorted()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredPlacements: StateFlow<List<PlacemarkEntity>> =
        allPlacemarks.combine(selectedTag) { places, tag ->
            places.filter {it.tagList().contains(tag)}
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        android.util.Log.d("MapViewModel", "ViewModel created, starting sync")
        viewModelScope.launch {
            try {
                android.util.Log.d("MapViewModel", "Calling syncFromApi")
                repository.syncFromApi()
                android.util.Log.d("MapViewModel", "Sync complete")
            } catch (t: Throwable) {  // ← change Exception to Throwable
                android.util.Log.e("MapViewModel", "Sync failed: ${t.message}", t)
            }
        }
    }

    fun selectTag(tag: String){
        selectedTag.value = tag
    }
}