package edu.nd.pmcburne.campusapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacemarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(placements: List<PlacemarkEntity>)

    @Query("SELECT * FROM placemarks")
    fun getAllPlacemarks(): Flow<List<PlacemarkEntity>>
}