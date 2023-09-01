package com.example.kud.data.repository

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat.getSystemService
import com.example.kud.utils.NetworkResult
//import com.example.kud.utils.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import xyz.teamgravity.checkinternet.CheckInternet
import java.io.IOException
import java.net.SocketTimeoutException


abstract class BaseRepo{

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    NetworkResult.Success(data = response.body()!!)
                } else {
                    val errorResponse =
                        JSONObject(response.errorBody()!!.charStream().readText())
                    NetworkResult.Error(errorResponse.toString() + "Wrong Email or Password")
                }
            } catch (e: HttpException) {
                NetworkResult.Error(e.message ?: "Something went wrong 1")
            } catch (e: IOException) {
//                No Connection or URL error or bad connection
                NetworkResult.Error(e.toString())
//                NetworkResult.Error("Request Time Out")
            } catch (e: Exception) {
//                NetworkResult.Error(e.toString() + "Url not found error code 404" )
                NetworkResult.Error("Url not found error code 404 $e")
            } catch (e: Throwable) {
                NetworkResult.Error(e.toString() + "Something went wrong 3")
            } catch (e: SocketTimeoutException) {
                NetworkResult.Error(e.message + "Socket time out exception")
            }
        }
    }

    private fun hasInternetConnection(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
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

//    private fun convertErrorBody(errorBody: ResponseBody?): ExampleErrorResponse? {
//        return try {
//            errorBody?.source()?.let {
//                val moshiAdapter = Moshi.Builder().build().adapter(ExampleErrorResponse::class.java)
//                moshiAdapter.fromJson(it)
//            }
//        } catch (exception: Exception) {
//            null
//        }
//    }
}

