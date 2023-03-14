package com.example.kud.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.kud.utils.TokenManager
import com.example.kud.utils.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = tokenManager.getToken()
        val tocen = userPreferences.accessToken
        request.addHeader("Authorization", "Bearer $token")
        request.addHeader("Accept", "application/json")
        return chain.proceed(request.build())
    }
}

//class AuthInterceptor @Inject constructor(context: Context) : Interceptor {
//
//    @Inject
//    lateinit var tokenManager: TokenManager
//
//    @Inject
//    lateinit var userPreferences: UserPreferences
//    private val applicationContext = context.applicationContext
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        if (!isInternetAvailable())
//            throw NoInternetException("Make sure you have an active data connection")
//        val request = chain.request().newBuilder()
//
//        val token = tokenManager.getToken()
//        val tocen = userPreferences.accessToken
//        request.addHeader("Authorization", "Bearer $tocen")
//        request.addHeader("Accept", "application/json")
//        return chain.proceed(request.build())
//    }
//
//    private fun isInternetAvailable(): Boolean {
//        var result = false
//        val connectivityManager =
//            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//        connectivityManager?.let {
//            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
//                result = when {
//                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//                    else -> false
//                }
//            }
//        }
//        return result
//    }
//
//}
//
//class ApiException(message: String) : IOException(message)
//class NoInternetException(message: String) : IOException(message)