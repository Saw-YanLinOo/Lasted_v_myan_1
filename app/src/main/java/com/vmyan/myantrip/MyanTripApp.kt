package com.vmyan.myantrip

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyanTripApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DeBUG){
//            Timber.plant(Timber.DebugTree())
//        }
    }

}