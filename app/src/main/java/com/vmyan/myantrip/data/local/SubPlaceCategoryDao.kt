package com.vmyan.myantrip.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.data.entities.SubPlaceCategory
import com.vmyan.myantrip.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface SubPlaceCategoryDao {

    @Query("SELECT * FROM ${Constants.SUBPLACECATEGORY_TABLE_NAME}")
    fun getAllSubPlaceCategory() : Flow<List<SubPlaceCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSubPlaceCategory(subPlaceCategory: List<SubPlaceCategory>)
}