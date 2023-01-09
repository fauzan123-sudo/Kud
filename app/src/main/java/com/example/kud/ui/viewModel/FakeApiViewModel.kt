package com.example.kud.ui.viewModel

import androidx.lifecycle.*
import com.example.kud.MyApplication
import com.example.kud.data.model.FakeApi
import com.example.kud.data.model.FakeApiItem
import com.example.kud.data.repository.RepositorySafe
import com.example.kud.data.repository.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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