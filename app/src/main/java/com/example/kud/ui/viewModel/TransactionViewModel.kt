package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.transaction.request.RequestAddPayment
import com.example.kud.data.model.transaction.response.addPayment.AddPaymentResponse
import com.example.kud.data.model.transaction.response.history.HistoryTransactionModel
import com.example.kud.data.repository.TransactionRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val repository: TransactionRepository) :
    ViewModel() {

    private val _getHistoryTransaction = MutableLiveData<NetworkResult<HistoryTransactionModel>>()
    val getHistoryTransaction: LiveData<NetworkResult<HistoryTransactionModel>> =
        _getHistoryTransaction

    private val _getAddPayment = MutableLiveData<NetworkResult<AddPaymentResponse>>()
    val getAddPayment: LiveData<NetworkResult<AddPaymentResponse>> =
        _getAddPayment

    fun requestHistoryTransaction(userId: Int) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getHistoryTransaction.postValue(NetworkResult.Loading())
                _getHistoryTransaction.postValue(repository.getHistoryTransaction(userId))
            } else
                _getHistoryTransaction.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun requestAddPayment(request: RequestAddPayment) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getAddPayment.postValue(NetworkResult.Loading())
                _getAddPayment.postValue(repository.getAddTransaction(request))
            } else
                _getAddPayment.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

}

