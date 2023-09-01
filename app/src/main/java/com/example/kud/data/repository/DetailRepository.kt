package com.example.kud.data.repository

import com.example.kud.data.model.detail.request.RequestToCart
import com.example.kud.data.model.detail.request.RequestToCheckOut
import com.example.kud.data.network.DetailApi
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: DetailApi) : BaseRepo() {

//    suspend fun addCart(body: RequestToCheckOut) =
//        safeApiCall { api.addToCheckOut(body) }

    suspend fun addCheckOut(body: RequestToCheckOut) =
        safeApiCall { api.addToCheckOut(body) }

    suspend fun detailDrug(id_drug: Int) = safeApiCall { api.getDetailDrug(id_drug) }
    suspend fun addToCart(request: RequestToCart) = safeApiCall { api.addToCart(request) }
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