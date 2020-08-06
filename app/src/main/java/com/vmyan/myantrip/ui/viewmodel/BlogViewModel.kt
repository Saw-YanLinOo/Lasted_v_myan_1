package com.vmyan.myantrip.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.vmyan.myantrip.data.entities.Post
import com.vmyan.myantrip.data.repository.BlogRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BlogViewModel @ViewModelInject constructor(
    private val repository: BlogRepository
) : ViewModel() {

    private val _postLiveData = MutableLiveData<Resource<List<Post>>>()

    val postLiveData : LiveData<Resource<List<Post>>>
    get() = _postLiveData

    fun getPost(){
        viewModelScope.launch {
            repository.getAllPost().collect{
                _postLiveData.value = it
            }
        }
    }

}