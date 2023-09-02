package com.example.kud.ui.viewModel

import androidx.lifecycle.*
import com.example.kud.data.repository.RepositorySafe
import com.example.kud.data.repository.ResultWrapper
import kotlinx.coroutines.launch

class FakeApiViewModel (
    private val repository: RepositorySafe
) : ViewModel(){

    private val _fakeApi = MutableLiveData<ResultWrapper<List<FakeApiItem>>>()
    val fakeApi: LiveData<ResultWrapper<List<FakeApiItem>>>
        get() = _fakeApi

    fun fake() {
        viewModelScope.launch {
            _fakeApi.postValue(repository.getPosts())
        }
    }
}