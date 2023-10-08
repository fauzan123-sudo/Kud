package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.address.list.UserAddressModel
import com.example.kud.data.model.checkOut.list.ListCheckOutModel
import com.example.kud.data.model.checkOut.plusMinus.PlusMinusModel
import com.example.kud.data.model.checkOut.request.RequestAddress
import com.example.kud.data.model.checkOut.request.RequestList
import com.example.kud.data.model.checkOut.request.RequestPlusMinus
import com.example.kud.data.repository.CheckOutRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(private val repository: CheckOutRepository) :
    ViewModel() {

    private val _getData = MutableLiveData<NetworkResult<ListCheckOutModel>>()
    val getData: LiveData<NetworkResult<ListCheckOutModel>> = _getData

    private val _getPlusMinus = MutableLiveData<NetworkResult<PlusMinusModel>>()
    val getPlusMinus: LiveData<NetworkResult<PlusMinusModel>> = _getPlusMinus

    private val _getAddress = MutableLiveData<NetworkResult<UserAddressModel>>()
    val getUserAddress: LiveData<NetworkResult<UserAddressModel>> = _getAddress

    fun requestList(request: RequestList) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getData.postValue(NetworkResult.Loading())
                _getData.postValue(repository.listCheckOut(request))
            } else
                _getData.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }

    fun requestPlusMinus(request: RequestPlusMinus) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getPlusMinus.postValue(NetworkResult.Loading())
                _getPlusMinus.postValue(repository.plusMinus(request))
            } else
                _getPlusMinus.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }

    fun requestUserAddress(request: RequestAddress) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getAddress.postValue(NetworkResult.Loading())
                _getAddress.postValue(repository.userAddress(request))
            } else
                _getAddress.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }
}

//class CheckOutViewModel @Inject constructor(val repository: CheckOutRepository) : ViewModel(),
//    LifecycleObserver {
//
//    val readData = repository.readData()
////    val readData = repository.readData().asLiveData()
//
//    fun insertData(checkOut: CheckOut) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.insertItem(checkOut)
//        }
//    }
//
//    fun deleteUser(checkOut: CheckOut) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteItem(checkOut)
//        }
//    }
//
//    fun deleteAllUser() {
//        viewModelScope.launch {
//            repository.deleteAllItem()
//        }
//    }
//
//    fun updateData(checkOut: CheckOut) {
//        viewModelScope.launch {
//            repository.updateItem(checkOut)
//        }
//    }
//
//}