package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.address.addOrEdit.AddEditAddressResponse
import com.example.kud.data.model.address.list.UserAddressModel
import com.example.kud.data.model.address.request.RequestAddOrEdit
import com.example.kud.data.model.address.request.RequestAddress
import com.example.kud.data.repository.AddressRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val repository: AddressRepository) :
    ViewModel() {

    private val _getAddress = MutableLiveData<NetworkResult<UserAddressModel>>()
    val getListAddress: LiveData<NetworkResult<UserAddressModel>> = _getAddress

    private val _getEdit = MutableLiveData<NetworkResult<AddEditAddressResponse>>()
    val getEdit: LiveData<NetworkResult<AddEditAddressResponse>> = _getEdit

    private val _getAdd = MutableLiveData<NetworkResult<AddEditAddressResponse>>()
    val getAddAddress: LiveData<NetworkResult<AddEditAddressResponse>> = _getAdd

    fun requestListUserAddress(request: RequestAddress) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getAddress.postValue(NetworkResult.Loading())
                _getAddress.postValue(repository.userAddress(request))
            } else
                _getAddress.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun requestEditAddress(request: RequestAddOrEdit) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getEdit.postValue(NetworkResult.Loading())
                _getEdit.postValue(repository.editAddress(request))
            } else
                _getEdit.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun requestAddAddress(request: RequestAddOrEdit) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getAdd.postValue(NetworkResult.Loading())
                _getAdd.postValue(repository.editAddress(request))
            } else
                _getAdd.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }
}

