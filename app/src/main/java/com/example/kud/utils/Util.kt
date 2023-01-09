package com.example.kud.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

    fun hasInternetConnection(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return result
    }


//    val network             = connectivityManager.activeNetwork
//    val activeNetwork       = connectivityManager.getNetworkCapabilities(network)


//@SuppressLint("ObsoleteSdkInt")
//fun hasInternetConnection(context: Context): Boolean {
//    val connectivityManager = context.getSystemService(
//        Context.CONNECTIVITY_SERVICE
//    ) as ConnectivityManager
//
//    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//        val activeNetwork = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//        return when{
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            else -> false
//        }
//    }
//    else{
//        connectivityManager.activeNetworkInfo?.run {
//            return when(type){
//                ConnectivityManager.TYPE_WIFI -> return true
//                ConnectivityManager.TYPE_MOBILE -> return true
//                ConnectivityManager.TYPE_ETHERNET -> return true
//                else -> false
//            }
//        }
//    }
//    return false
//
//}



