package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.allProduct.response.CategoryResponse
import com.example.kud.data.model.home.list.ListDrugModel
import com.example.kud.data.model.product.DetailProductResponse
import com.example.kud.data.repository.HomeRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val _searchDrug = MutableLiveData<NetworkResult<ListDrugModel>>()
    val searchDrug: LiveData<NetworkResult<ListDrugModel>> = _searchDrug

    private val _getCategory = MutableLiveData<NetworkResult<CategoryResponse>>()
    val getCategory: LiveData<NetworkResult<CategoryResponse>> = _getCategory

    private val _getDrug = MutableLiveData<NetworkResult<ListDrugModel>>()
    val getDrug: LiveData<NetworkResult<ListDrugModel>> = _getDrug

    private val _getDetailDrug = MutableLiveData<NetworkResult<DetailProductResponse>>()
    val getDetailDrug: LiveData<NetworkResult<DetailProductResponse>> = _getDetailDrug

    fun requestSearchDrug(q:String?, k:String?) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _searchDrug.postValue(NetworkResult.Loading())
                _searchDrug.postValue(repository.searchDrug(q,k))
            } else
                _searchDrug.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }

    fun category() {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getCategory.postValue(NetworkResult.Loading())
                _getCategory.postValue(repository.category())
            } else
                _getCategory.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun drug() {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getDrug.postValue(NetworkResult.Loading())
                _getDrug.postValue(repository.drug())
            } else
                _getCategory.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun detailDrug(id_drug:Int) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _getDetailDrug.postValue(NetworkResult.Loading())
                _getDetailDrug.postValue(repository.detailDrug(id_drug))
            } else
                _getDetailDrug.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }
}

