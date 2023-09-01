package com.example.kud.data.repository

import com.example.kud.data.model.checkOut.request.RequestAddress
import com.example.kud.data.model.checkOut.request.RequestPlusMinus
import com.example.kud.data.network.CheckOutApi
import javax.inject.Inject

class CheckOutRepository @Inject constructor(private val api: CheckOutApi) : BaseRepo() {


    suspend fun listCheckOut(userId: String) =
        safeApiCall { api.getListCheckOut(userId) }

    suspend fun plusMinus(request: RequestPlusMinus) =
        safeApiCall { api.plusMinus(request) }

    suspend fun userAddress(request: RequestAddress) =
        safeApiCall { api.userAddress(request) }
}

//class CheckOutRepository @Inject constructor(private val dao: CheckOutDao) {
//
//    fun readData(): LiveData<List<CheckOut>> {
//        return dao.getAllData()
//    }
//
//    suspend fun insertItem(checkOut: CheckOut) {
//        dao.insertItems(checkOut)
//    }
//
//
////    val readData: LiveData<List<User>> = myDao.getAllData()
//
//    suspend fun updateItem(checkOut: CheckOut){
//        dao.updateItem(checkOut)
//    }
//
//    suspend fun deleteItem(checkOut: CheckOut){
//        dao.delete(checkOut)
//    }
//
//    suspend fun deleteAllItem(){
//        dao.deleteAllItem()
//    }
//}