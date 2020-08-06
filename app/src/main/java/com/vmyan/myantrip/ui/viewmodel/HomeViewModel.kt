package com.vmyan.myantrip.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.vmyan.myantrip.data.entities.Place
import com.vmyan.myantrip.data.entities.PlaceCategory
import com.vmyan.myantrip.data.entities.SubPlaceCategory
import com.vmyan.myantrip.data.repository.HomeRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class HomeViewModel @ViewModelInject constructor(
    private val repository: HomeRepository
) : ViewModel() {


    private val _placeCategoryLiveData = MutableLiveData<Resource<List<PlaceCategory>>>()
    private val _subPlaceCategoryLiveData = MutableLiveData<Resource<List<SubPlaceCategory>>>()
    private val _placeLiveData = MutableLiveData<Resource<List<Place>>>()
    private val _rplaceLiveData = MutableLiveData<Resource<List<Place>>>()
    private val _tPplaceLiveData = MutableLiveData<Resource<List<Place>>>()
    private val _subplaceLiveData = MutableLiveData<Resource<List<Place>>>()


    val placeCategoryLiveData: LiveData<Resource<List<PlaceCategory>>>
        get() = _placeCategoryLiveData
    val subPlaceCategoryLiveData: LiveData<Resource<List<SubPlaceCategory>>>
        get() = _subPlaceCategoryLiveData
    val placeLiveData: LiveData<Resource<List<Place>>>
        get() = _placeLiveData
    val rplaceLiveData: LiveData<Resource<List<Place>>>
        get() = _rplaceLiveData
    val tPplaceLiveData: LiveData<Resource<List<Place>>>
        get() = _tPplaceLiveData
    val subplaceLiveData: LiveData<Resource<List<Place>>>
        get() = _subplaceLiveData

    fun getPlaceCategory(){
        viewModelScope.launch {
            repository.getAllPlaceCategory().collect {
                _placeCategoryLiveData.value = it
            }
        }
    }

    fun getSubPlaceCategory() {
        viewModelScope.launch {
            repository.getAllSubPlaceCategory().collect {
                _subPlaceCategoryLiveData.value = it
            }
        }
    }

    fun getPlace() {
        viewModelScope.launch {
            repository.getAllPlace().collect {
                _placeLiveData.value = it
            }
        }
    }

    fun getRecommendedPlace() {
        viewModelScope.launch {
            repository.getRecommendedPlace().collect {
                _rplaceLiveData.value = it
            }
        }
    }

    fun getTopRatingPlace() {
        viewModelScope.launch {
            repository.getTopRatingPlace().collect {
                _tPplaceLiveData.value = it
            }
        }
    }

    fun getSubCategoryPlaces(name:String) {
        viewModelScope.launch {
            repository.getSubCategoryPlaces(name).collect {
                _subplaceLiveData.value = it
            }
        }
    }


}