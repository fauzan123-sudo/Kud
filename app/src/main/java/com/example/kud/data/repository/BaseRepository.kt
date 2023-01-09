package com.example.kud.data.repository

import com.example.kud.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResult.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        NetworkResult.Error(throwable.code().toString(), null)
                    }
                    else -> {
                        NetworkResult.Error("Something error", null)
                    }
                }
            }
        }

    }
//
//    suspend fun logOut(api: UserApi) = safeApiCall {
//        api.logOut()
//    }

}