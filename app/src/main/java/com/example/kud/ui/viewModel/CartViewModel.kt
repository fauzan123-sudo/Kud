package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.transaction.AddCartResponse
import com.example.kud.data.model.transaction.CartRequest
import com.example.kud.data.repository.TransactionRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: TransactionRepository) :
    ViewModel() {

    private val _getData = MutableLiveData<NetworkResult<AddCartResponse>>()
    val getData: LiveData<NetworkResult<AddCartResponse>> = _getData

    fun addCart(body: CartRequest) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getData.postValue(NetworkResult.Loading())
                _getData.postValue(repository.getCart(body))
            } else
                _getData.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }
}

