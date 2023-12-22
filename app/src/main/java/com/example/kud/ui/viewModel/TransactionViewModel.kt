package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.transaction.detail.DetailTransactionResponse
import com.example.kud.data.model.transaction.request.RequestAddPayment
import com.example.kud.data.model.transaction.response.addPayment.AddPaymentResponse
import com.example.kud.data.model.transaction.response.history.HistoryTransactionModel
import com.example.kud.data.model.upload.ImageUploadResponse
import com.example.kud.data.repository.TransactionRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    private val _uploadImage = MutableLiveData<NetworkResult<ImageUploadResponse>>()
    val uploadImage: LiveData<NetworkResult<ImageUploadResponse>> =
        _uploadImage

    private val _detailTransactionResponse =
        MutableLiveData<NetworkResult<DetailTransactionResponse>>()
    val detailTransactionResponse: LiveData<NetworkResult<DetailTransactionResponse>> =
        _detailTransactionResponse

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

    fun requestImageUpload(
        transactionCode: RequestBody,
        file: MultipartBody.Part
    ) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _uploadImage.postValue(NetworkResult.Loading())
                _uploadImage.postValue(repository.imageUpload(transactionCode, file))
            } else
                _uploadImage.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun requestDetailTransaction(transactionCode: String) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _detailTransactionResponse.postValue(NetworkResult.Loading())
                _detailTransactionResponse.postValue(repository.getDetailTransaction(transactionCode))
            } else
                _detailTransactionResponse.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }
}



