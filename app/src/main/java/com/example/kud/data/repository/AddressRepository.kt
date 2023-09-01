package com.example.kud.data.repository

import com.example.kud.data.model.address.request.RequestAddress
import com.example.kud.data.network.AddressApi
import javax.inject.Inject

class AddressRepository @Inject constructor(private val api: AddressApi) : BaseRepo() {

    suspend fun userAddress(body: RequestAddress) =
        safeApiCall { api.userAddress(body) }
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