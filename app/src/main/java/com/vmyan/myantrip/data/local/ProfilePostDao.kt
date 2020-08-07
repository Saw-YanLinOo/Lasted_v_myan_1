package com.vmyan.myantrip.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.data.entities.ProfilePost
import com.vmyan.myantrip.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfilePostDao {
    @Query("SELECT * FROM ${Constants.PROFILE_POST_TABLE_NAME}")
    fun getAllPost() : Flow<List<ProfilePost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPost(postList: List<ProfilePost>)
}