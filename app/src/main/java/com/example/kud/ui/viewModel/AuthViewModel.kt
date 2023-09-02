package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.ProfileResponse
import com.example.kud.data.model.auth.login.LoginRequest
import com.example.kud.data.model.auth.login.LoginResponse
import com.example.kud.data.model.auth.logout.LogOutResponse
import com.example.kud.data.repository.AuthRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) :
    ViewModel() {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<LoginResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<LoginResponse>>
        get() = _userResponseLiveData

    private val _profileResponseLiveData = MutableLiveData<NetworkResult<ProfileResponse>>()
    val profileResponseLiveData: LiveData<NetworkResult<ProfileResponse>>
        get() = _profileResponseLiveData

    private val _logoutLiveData = MutableLiveData<NetworkResult<LogOutResponse>>()
    val logoutLiveData: LiveData<NetworkResult<LogOutResponse>>
        get() = _logoutLiveData

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _userResponseLiveData.postValue(NetworkResult.Loading())
                _userResponseLiveData.postValue(repository.loginUser(loginRequest))
            } else
                _userResponseLiveData.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun logoutRequest() {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _logoutLiveData.postValue(NetworkResult.Loading())
                _logoutLiveData.postValue(repository.logOut())
            } else
                _logoutLiveData.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    fun profileUser() {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _profileResponseLiveData.postValue(NetworkResult.Loading())
                _profileResponseLiveData.postValue(repository.profileUser())
            } else
                _profileResponseLiveData.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }

    suspend fun handlingApi(loginRequest: LoginRequest) {
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