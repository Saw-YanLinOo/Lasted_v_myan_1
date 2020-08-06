package com.vmyan.myantrip.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.data.entities.Word
import com.vmyan.myantrip.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM ${Constants.WORD_TABLE_NAME}")
    fun getAllWords() : Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWords(wordList: List<Word>)
}