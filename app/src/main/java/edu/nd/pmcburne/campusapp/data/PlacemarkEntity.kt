package edu.nd.pmcburne.campusapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlacemarkResponseObject(
    val id: Int,
    val name: String,
    @SerialName("tag_list") val tagList: List<String>,
    val description: String = "",
    @SerialName("visual_center") val visualCenter: VisualCenter
)

@Serializable
data class VisualCenter(
    val latitude: Double,
    val longitude: Double
)

@Entity(tableName="placemarks")
data class PlacemarkEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val tags: String
)

fun PlacemarkResponseObject.toEntity() = PlacemarkEntity(
    id = id,
    name = name,
    description = description,
    latitude = visualCenter.latitude,
    longitude = visualCenter.longitude,
    tags = tagList.joinToString(",")
)

fun PlacemarkEntity.tagList(): List<String> =
    tags.split(",").map {it.trim()}.filter{it.isNotEmpty()}