package com.vmyan.myantrip.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.data.entities.Place
import com.vmyan.myantrip.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM ${Constants.PLACE_TABLE_NAME}")
    fun getAllPlace() : Flow<List<Place>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlace(palce: List<Place>)

    @Query("SELECT * FROM ${Constants.PLACE_TABLE_NAME} WHERE isRecommended LIKE 'true' ORDER BY place_id DESC")
    fun getRecommendedPlace() : Flow<List<Place>>

    @Query("SELECT * FROM ${Constants.PLACE_TABLE_NAME} WHERE review_id >= 4 ORDER BY review_id DESC")
    fun getTopRating() : Flow<List<Place>>

    @Query("SELECT * FROM ${Constants.PLACE_TABLE_NAME} WHERE place_sub_cat1 = :name OR place_sub_cat2 = :name OR place_sub_cat3 = :name ORDER BY review_id DESC")
    fun getSubCategoryPlaces(name: String) : Flow<List<Place>>
}