package com.vmyan.myantrip.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.data.entities.PlaceCategory
import com.vmyan.myantrip.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceCategoryDao {

    @Query("SELECT * FROM ${Constants.PLACECATEGORY_TABLE_NAME}")
    fun getAllPlaceCategory() : Flow<List<PlaceCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlaceCategory(placeCategory: List<PlaceCategory>)
}