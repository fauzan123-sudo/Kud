package com.example.kud.data.repository

import com.example.kud.data.model.cart.request.RequestCheckUnCheck
import com.example.kud.data.model.cart.request.RequestDeleteDrug
import com.example.kud.data.model.cart.request.RequestListCart
import com.example.kud.data.model.cart.request.RequestPlusMinus
import com.example.kud.data.model.transaction.CartRequest
import com.example.kud.data.network.CartApi
import javax.inject.Inject

class CartRepository @Inject constructor(private val api: CartApi) : BaseRepo() {

    suspend fun addCart(body: CartRequest) =
        safeApiCall { api.addCart(body) }

    suspend fun listOfCart(request: RequestListCart) =
        safeApiCall { api.listOfCart(request) }

    suspend fun plusMinus(request: RequestPlusMinus) =
        safeApiCall { api.plusMinus(request) }

    suspend fun deleteDrug(request: RequestDeleteDrug) =
        safeApiCall { api.deleteDrug(request) }

    suspend fun checkUnCheck(request: RequestCheckUnCheck) =
        safeApiCall { api.checkUnCheck(request) }
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