package com.vmyan.myantrip.data.repository

import com.vmyan.myantrip.data.entities.Place
import com.vmyan.myantrip.data.entities.PlaceCategory
import com.vmyan.myantrip.data.entities.SubPlaceCategory
import com.vmyan.myantrip.data.local.PlaceCategoryDao
import com.vmyan.myantrip.data.local.PlaceDao
import com.vmyan.myantrip.data.local.SubPlaceCategoryDao
import com.vmyan.myantrip.data.remote.ApiService
import com.vmyan.myantrip.utils.NetworkBoundRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val subPlaceCategoryDao: SubPlaceCategoryDao,
    private val placeCategoryDao: PlaceCategoryDao,
    private val placeDao: PlaceDao
){

    fun getAllPlaceCategory(): Flow<Resource<List<PlaceCategory>>> {
        return object : NetworkBoundRepository<List<PlaceCategory>, List<PlaceCategory>>() {

            override suspend fun saveRemoteData(response: List<PlaceCategory>) =
                placeCategoryDao.insertAllPlaceCategory(response)

            override fun fetchFromLocal(): Flow<List<PlaceCategory>> = placeCategoryDao.getAllPlaceCategory()

            override suspend fun fetchFromRemote(): Response<List<PlaceCategory>> = apiService.getAllPlaceCategory()

        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getAllSubPlaceCategory(): Flow<Resource<List<SubPlaceCategory>>> {
        return object : NetworkBoundRepository<List<SubPlaceCategory>, List<SubPlaceCategory>>() {

            override suspend fun saveRemoteData(response: List<SubPlaceCategory>) =
                subPlaceCategoryDao.insertAllSubPlaceCategory(response)

            override fun fetchFromLocal(): Flow<List<SubPlaceCategory>> = subPlaceCategoryDao.getAllSubPlaceCategory()

            override suspend fun fetchFromRemote(): Response<List<SubPlaceCategory>> = apiService.getAllSubPlaceCategory()

        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getAllPlace(): Flow<Resource<List<Place>>> {
        return object : NetworkBoundRepository<List<Place>, List<Place>>() {

            override suspend fun saveRemoteData(response: List<Place>) =
                placeDao.insertAllPlace(response)

            override fun fetchFromLocal(): Flow<List<Place>> = placeDao.getAllPlace()

            override suspend fun fetchFromRemote(): Response<List<Place>> = apiService.getAllPlace()

        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getRecommendedPlace(): Flow<Resource<List<Place>>> {
        return object : NetworkBoundRepository<List<Place>, List<Place>>() {

            override suspend fun saveRemoteData(response: List<Place>) {

            }

            override fun fetchFromLocal(): Flow<List<Place>> = placeDao.getRecommendedPlace()

            override suspend fun fetchFromRemote(): Response<List<Place>> = apiService.getRecommendedPlace()

        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getTopRatingPlace(): Flow<Resource<List<Place>>> {
        return object : NetworkBoundRepository<List<Place>, List<Place>>() {

            override suspend fun saveRemoteData(response: List<Place>) {

            }

            override fun fetchFromLocal(): Flow<List<Place>> = placeDao.getTopRating()

            override suspend fun fetchFromRemote(): Response<List<Place>> = apiService.getTopRatingPlace()

        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getSubCategoryPlaces(name:String): Flow<Resource<List<Place>>> {
        return object : NetworkBoundRepository<List<Place>, List<Place>>() {

            override suspend fun saveRemoteData(response: List<Place>) {

            }

            override fun fetchFromLocal(): Flow<List<Place>> = placeDao.getSubCategoryPlaces(name)

            override suspend fun fetchFromRemote(): Response<List<Place>> = apiService.getSubCategoryPlaces(name)

        }.asFlow().flowOn(Dispatchers.IO)
    }
}