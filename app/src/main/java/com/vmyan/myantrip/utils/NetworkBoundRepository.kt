package com.vmyan.myantrip.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.*
import retrofit2.Response

/**
 * A repository which provides resource from local database as well as remote end point.
 *
 * [RESULT] represents the type for database.
 * [REQUEST] represents the type for network.
 */
abstract class NetworkBoundRepository<RESULT, REQUEST> {

    fun asFlow() = flow<Resource<RESULT>> {


        emit(Resource.loading())

        try {
            // Emit Database content first
            emit(Resource.success(fetchFromLocal().first()))

            // Fetch latest posts from remote
            val apiResponse = fetchFromRemote()

            // Parse body
            val remoteData = apiResponse.body()

            // Check for response validation
            if (apiResponse.isSuccessful && remoteData != null) {
                // Save posts into the persistence storage
                saveRemoteData(remoteData)
            } else {
                // Something went wrong! Emit Error state.
                emit(Resource.error(apiResponse.message()))
            }
        } catch (e: Exception) {
            // Exception occurred! Emit error
            emit(Resource.error("Network error! Can't get latest posts."))
            e.printStackTrace()
        }

        // Retrieve posts from persistence storage and emit
        emitAll(fetchFromLocal().map {
            Resource.success<RESULT>(it)
        })
    }

    /**
     * Saves retrieved from remote into the persistence storage.
     */
    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

    /**
     * Retrieves all data from persistence storage.
     */
    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>
}