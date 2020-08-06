package com.vmyan.myantrip.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.data.entities.Post
import com.vmyan.myantrip.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM ${Constants.POST_TABLE_NAME}")
    fun getAllPost() : Flow<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPost(postList: List<Post>)
}