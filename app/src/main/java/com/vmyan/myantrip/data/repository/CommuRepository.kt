package com.vmyan.myantrip.data.repository

import com.vmyan.myantrip.data.entities.Word
import com.vmyan.myantrip.data.local.WordDao
import com.vmyan.myantrip.data.remote.ApiService
import com.vmyan.myantrip.utils.NetworkBoundRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class CommuRepository @Inject constructor(
    private val apiService: ApiService,
    private val wordDao: WordDao
) {
    fun getWord(): Flow<Resource<List<Word>>>{
        return object : NetworkBoundRepository<List<Word>,List<Word>>(){
            override suspend fun saveRemoteData(response: List<Word>) =
                wordDao.insertAllWords(response)

            override fun fetchFromLocal(): Flow<List<Word>> = wordDao.getAllWords()

            override suspend fun fetchFromRemote(): Response<List<Word>> = apiService.getWord()

        }.asFlow().flowOn(Dispatchers.IO)
    }
}