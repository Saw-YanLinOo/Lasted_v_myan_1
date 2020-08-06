package com.vmyan.myantrip.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vmyan.myantrip.data.entities.Word
import com.vmyan.myantrip.data.repository.CommuRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommuViewModel @ViewModelInject constructor(
    private val repository: CommuRepository
) : ViewModel() {
    private val _wordLiveData = MutableLiveData<Resource<List<Word>>>()

    val wordLiveData : LiveData<Resource<List<Word>>>
    get() = _wordLiveData

    fun getWord(){
        viewModelScope.launch {
            repository.getWord().collect{
                _wordLiveData.value = it
            }
        }
    }
}