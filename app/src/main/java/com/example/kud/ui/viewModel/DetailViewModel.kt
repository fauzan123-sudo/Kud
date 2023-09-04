package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.detail.addCheckOut.AddToCheckOutModel
import com.example.kud.data.model.detail.request.RequestToCart
import com.example.kud.data.model.detail.request.RequestToCheckOut
import com.example.kud.data.model.product.DetailProductResponse
import com.example.kud.data.model.transaction.response.AddCartResponse
import com.example.kud.data.repository.DetailRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) :
    ViewModel() {

    private val _getDetailDrug = MutableLiveData<NetworkResult<DetailProductResponse>>()
    val getDetailDrug: LiveData<NetworkResult<DetailProductResponse>> = _getDetailDrug

    private val _addToCart = MutableLiveData<NetworkResult<AddCartResponse>>()
    val addToCart: LiveData<NetworkResult<AddCartResponse>> = _addToCart

    private val _addData = MutableLiveData<NetworkResult<AddToCheckOutModel>>()
    val addDataCheckOut: LiveData<NetworkResult<AddToCheckOutModel>> = _addData

    fun requestDetailDrug(id_drug: Int) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getDetailDrug.postValue(NetworkResult.Loading())
                _getDetailDrug.postValue(repository.detailDrug(id_drug))
            } else
                _getDetailDrug.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun addToCartRequest(request: RequestToCart) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _addToCart.postValue(NetworkResult.Loading())
                _addToCart.postValue(repository.addToCart(request))
            } else
                _addToCart.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun requestToCheckOut(body: RequestToCheckOut) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _addData.postValue(NetworkResult.Loading())
                _addData.postValue(repository.addCheckOut(body))
            } else
                _addData.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }
}

