package com.example.kud.data.repository

import com.example.kud.data.model.LoginRequest
import com.example.kud.data.network.AuthApi
import com.example.kud.data.network.UserApi
import com.example.kud.utils.NetworkResult
import javax.inject.Inject


class AuthRepository @Inject constructor(private val api : AuthApi) : BaseRepo() {

    suspend fun loginUser(loginRequest: LoginRequest) = safeApiCall { api.loginUser(loginRequest) }
    suspend fun profileUser() = safeApiCall { api.profileUser() }
    suspend fun logOut() = safeApiCall { api.logout() }

//    suspend fun loginUser(loginRequest: LoginRequest) = safeApiCall { userApi.loginUser(loginRequest) }

//    private val _userResponseLiveData = MutableLiveData<NetworkResult<LoginResponse>>()
//    val userResponseLiveData: LiveData<NetworkResult<LoginResponse>>
//        get() = _userResponseLiveData
//
//    suspend fun loginUser(loginRequest: LoginRequest) {
//        _userResponseLiveData.postValue(NetworkResult.Loading())
//        val response = userApi.loginUser(loginRequest)
//        handleApi(response)
//    }
//
//    private fun handleApi(response: Response<LoginResponse>) {
//        if (response.isSuccessful && response.body() != null) {
//            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
//        } else if (response.errorBody() != null) {
//            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
//        } else {
//            _userResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
//        }
//    }

}