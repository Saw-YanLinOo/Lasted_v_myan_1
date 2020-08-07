package com.vmyan.myantrip.data.repository

import com.vmyan.myantrip.data.entities.Post
import com.vmyan.myantrip.data.entities.ProfilePost
import com.vmyan.myantrip.data.local.PostDao
import com.vmyan.myantrip.data.local.ProfilePostDao
import com.vmyan.myantrip.data.remote.ApiService
import com.vmyan.myantrip.utils.NetworkBoundRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class BlogRepository @Inject constructor(
    private val apiService: ApiService,
    private val postDao: PostDao,
    private val profilePostDao: ProfilePostDao
) {
    fun getAllPost() : Flow<Resource<List<Post>>>{
        return object : NetworkBoundRepository<List<Post>,List<Post>>(){
            override suspend fun saveRemoteData(response: List<Post>) =
                postDao.insertAllPost(response)

            override fun fetchFromLocal(): Flow<List<Post>> = postDao.getAllPost()

            override suspend fun fetchFromRemote(): Response<List<Post>> = apiService.getAllPost()

        }.asFlow().flowOn(Dispatchers.IO)
    }
    fun getPostByUserId(userId:Int):Flow<Resource<List<ProfilePost>>>{
        return object : NetworkBoundRepository<List<ProfilePost>,List<ProfilePost>>(){
            override suspend fun saveRemoteData(response: List<ProfilePost>) =
                profilePostDao.insertAllPost(response)

            override fun fetchFromLocal(): Flow<List<ProfilePost>> = profilePostDao.getAllPost()

            override suspend fun fetchFromRemote(): Response<List<ProfilePost>> = apiService.getPostByUserId(userId)

        }.asFlow().flowOn(Dispatchers.IO)
    }
}