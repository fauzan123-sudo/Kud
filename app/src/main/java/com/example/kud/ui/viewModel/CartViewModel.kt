package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.cart.checkUncheck.CheckUnCheckResponse
import com.example.kud.data.model.cart.delete.DeleteDrugResponse
import com.example.kud.data.model.cart.list.ListCartModel
import com.example.kud.data.model.cart.plusMinus.PlusMinusModel
import com.example.kud.data.model.cart.request.RequestCheckUnCheck
import com.example.kud.data.model.cart.request.RequestDeleteDrug
import com.example.kud.data.model.cart.request.RequestListCart
import com.example.kud.data.model.cart.request.RequestPlusMinus
import com.example.kud.data.model.transaction.AddCartResponse
import com.example.kud.data.model.transaction.CartRequest
import com.example.kud.data.repository.CartRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) :
    ViewModel() {

    private val _getData = MutableLiveData<NetworkResult<AddCartResponse>>()
    val getData: LiveData<NetworkResult<AddCartResponse>> = _getData

    private val _getList = MutableLiveData<NetworkResult<ListCartModel>>()
    val getList: LiveData<NetworkResult<ListCartModel>> = _getList

    private val _getPlusMinus = MutableLiveData<NetworkResult<PlusMinusModel>>()
    val getPlusMinus: LiveData<NetworkResult<PlusMinusModel>> = _getPlusMinus

    private val _getDeleteDrug = MutableLiveData<NetworkResult<DeleteDrugResponse>>()
    val getDeleteDrug: LiveData<NetworkResult<DeleteDrugResponse>> = _getDeleteDrug

    private val _getCheckUnCheck = MutableLiveData<NetworkResult<CheckUnCheckResponse>>()
    val getCheckUnCheck: LiveData<NetworkResult<CheckUnCheckResponse>> = _getCheckUnCheck

    fun addCart(body: CartRequest) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getData.postValue(NetworkResult.Loading())
                _getData.postValue(repository.addCart(body))
            } else
                _getData.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun listOfCart(request: RequestListCart) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getList.postValue(NetworkResult.Loading())
                _getList.postValue(repository.listOfCart(request))
            } else
                _getList.postValue(NetworkResult.Error("No Internet Connection"))
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

    fun requestDeleteDrug(request: RequestDeleteDrug) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getDeleteDrug.postValue(NetworkResult.Loading())
                _getDeleteDrug.postValue(repository.deleteDrug(request))
            } else
                _getDeleteDrug.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }

    fun requestCheckUnCheck(request: RequestCheckUnCheck) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getCheckUnCheck.postValue(NetworkResult.Loading())
                _getCheckUnCheck.postValue(repository.checkUnCheck(request))
            } else
                _getCheckUnCheck.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }
}

