package com.vmyan.myantrip.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vmyan.myantrip.data.local.*
import com.vmyan.myantrip.data.remote.ApiService
import com.vmyan.myantrip.data.repository.BlogRepository
import com.vmyan.myantrip.data.repository.CommuRepository
import com.vmyan.myantrip.data.repository.HomeRepository
import com.vmyan.myantrip.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideGson() : Gson = GsonBuilder().create()

    @Provides
    fun privideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideSubPlaceCategoryDao(db: AppDatabase) = db.subPlaceCategoryDao()

    @Singleton
    @Provides
    fun providePlaceCategoryDao(db: AppDatabase) = db.placeCategoryDao()

    @Singleton
    @Provides
    fun providePlaceDao(db: AppDatabase) = db.placeDao()

    @Singleton
    @Provides
    fun providePostDao(db: AppDatabase) = db.postDao()

    @Singleton
    @Provides
    fun provideWordDao(db: AppDatabase) = db.wordDao()

    @Singleton
    @Provides
    fun provideHomeRepository(apiService: ApiService,
                                subPlaceCategoryDao: SubPlaceCategoryDao,
                                placeCategoryDao: PlaceCategoryDao,
                                placeDao: PlaceDao) =
        HomeRepository(apiService, subPlaceCategoryDao, placeCategoryDao, placeDao)

    @Singleton
    @Provides
    fun provideBlogRepository(apiService: ApiService,
                                postDao: PostDao )=
        BlogRepository(apiService, postDao)

    @Singleton
    @Provides
    fun provideCommuRepository(apiService: ApiService,
                                wordDao: WordDao )=
        CommuRepository(apiService, wordDao)
}