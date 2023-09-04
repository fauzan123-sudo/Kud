package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.auth.login.LoginRequest
import com.example.kud.data.model.auth.login.LoginResponse
import com.example.kud.data.model.auth.logout.LogOutResponse
import com.example.kud.data.model.auth.register.response.RegisterResponse
import com.example.kud.data.repository.AuthRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) :
    ViewModel() {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<LoginResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<LoginResponse>>
        get() = _userResponseLiveData

    private val _registerUser = MutableLiveData<NetworkResult<RegisterResponse>>()
    val getRegisterUser: LiveData<NetworkResult<RegisterResponse>>
        get() = _registerUser

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

    fun registerUser( name: String,
                      email: String,
                      password: String,
                      alamat: String,) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _registerUser.postValue(NetworkResult.Loading())
                val result = repository.registerUser(name, email, password, alamat)
                _registerUser.postValue(result)
            } else
                _registerUser.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }
}