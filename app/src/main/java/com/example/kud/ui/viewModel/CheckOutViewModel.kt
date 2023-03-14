package com.example.kud.ui.viewModel

import androidx.lifecycle.*
import com.example.kud.data.model.CheckOut
import com.example.kud.data.model.DataRequest
import com.example.kud.data.model.RequestData
import com.example.kud.data.model.User
import com.example.kud.data.repository.CheckOutRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(val repository: CheckOutRepository) : ViewModel(),
    LifecycleObserver {

    val readData = repository.readData()
//    val readData = repository.readData().asLiveData()

    fun insertData(checkOut: CheckOut) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertItem(checkOut)
        }
    }

    fun deleteUser(checkOut: CheckOut) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(checkOut)
        }
    }

    fun deleteAllUser() {
        viewModelScope.launch {
            repository.deleteAllItem()
        }
    }

    fun updateData(checkOut: CheckOut) {
        viewModelScope.launch {
            repository.updateItem(checkOut)
        }
    }

}