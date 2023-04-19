package com.example.kud.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kud.MyApplication
import com.example.kud.data.model.LoginRequest
import com.example.kud.data.model.LoginResponse
import com.example.kud.data.model.ProfileResponse
import com.example.kud.data.repository.UserRepository
import com.example.kud.utils.Connections.hasInternetConnection
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(app: Application, private val repository: UserRepository) :
    AndroidViewModel(app) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<LoginResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<LoginResponse>>
        get() = _userResponseLiveData

    private val _profileResponseLiveData = MutableLiveData<NetworkResult<ProfileResponse>>()
    val profileResponseLiveData: LiveData<NetworkResult<ProfileResponse>>
        get() = _profileResponseLiveData

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            if (hasInternetConnection(getApplication<MyApplication>())) {
                _userResponseLiveData.postValue(NetworkResult.Loading())
                _userResponseLiveData.postValue(repository.loginUser(loginRequest))
            } else
                _userResponseLiveData.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }

    fun profileUser() {
        viewModelScope.launch {
            if (hasInternetConnection(getApplication<MyApplication>())) {
                _profileResponseLiveData.postValue(NetworkResult.Loading())
                _profileResponseLiveData.postValue(repository.profileUser())
            } else
                _profileResponseLiveData.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }

//    fun login(loginRequest: LoginRequest) {
//        viewModelScope.launch {
//            if (hasInternetConnection(getApplication<MyApplication>())) {
//                val response = repository.loginUser(loginRequest)
//                _userResponseLiveData.postValue(NetworkResult.Success(response))
//                _userResponseLiveData.value = NetworkResult.Success(response)
//            } else
//                _userResponseLiveData.postValue(NetworkResult.Error("No Internet Connection"))
//        }
//
//    }

//    fun login(loginRequest: LoginRequest) {
//        viewModelScope.launch {
//            _userResponseLiveData.postValue(NetworkResult.Loading())
//            viewModelScope.launch {
//                try {
//                    if (hasInternetConnection(getApplication<MyApplication>())) {
//                        val response = repository.loginUser(loginRequest)
//                        _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
//                    } else
//                        _userResponseLiveData.postValue(NetworkResult.Error("No Internet Connection"))
//                } catch (ex: Exception) {
//                    when (ex) {
//                        is IOException -> _userResponseLiveData.postValue(NetworkResult.Error("Network Failure " + ex.localizedMessage))
//                        else -> _userResponseLiveData.postValue(NetworkResult.Error("Conversion Error"))
//                    }
//                }
//            }
//
//
////            handlingApi(loginRequest)
//        }
//    }

    suspend fun handlingApi(loginRequest: LoginRequest) {
//        _userResponseLiveData.postValue(NetworkResult.Loading())
//        try {
//            if (hasInternetConnection(getApplication<MyApplication>())) {
//                val response = repository.loginUser(loginRequest)
//                _userResponseLiveData.postValue(handlePicsResponse(response))
//            } else {
//                _userResponseLiveData.postValue(
//                    NetworkResult.Error(
//                        (getApplication<MyApplication>().getString(
//                            R.string.no_internet_connection
//                        ))
//                    )
//                )
//            }
//        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> {
//                    _userResponseLiveData.postValue(
//                        NetworkResult.Error(
//                            getApplication<MyApplication>()
//                                .getString(R.string.network_failure)
//                        )
//                    )
//                }
//
//                else -> _userResponseLiveData.postValue(
//                    NetworkResult.Error(
//                        getApplication<MyApplication>()
//                            .getString(R.string.conversion_error)
//                    )
//                )
//            }
//        }

    }

    private fun handlePicsResponse(response: Response<LoginResponse>): NetworkResult<LoginResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return (NetworkResult.Success(resultResponse))
            }
        }
        return (NetworkResult.Error(response.message()))
    }

}