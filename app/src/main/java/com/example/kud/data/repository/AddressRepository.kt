package com.example.kud.data.repository

import com.example.kud.data.model.address.request.RequestAddOrEdit
import com.example.kud.data.model.address.request.RequestAddress
import com.example.kud.data.model.address.request.RequestEditAddress
import com.example.kud.data.model.address.request.RequestSetAddress
import com.example.kud.data.network.AddressApi
import javax.inject.Inject

class AddressRepository @Inject constructor(private val api: AddressApi) : BaseRepo() {

    suspend fun userAddress(body: RequestAddress) =
        safeApiCall { api.userAddress(body) }
    suspend fun editAddress(request: RequestEditAddress) =
        safeApiCall { api.editAddress(request) }

    suspend fun addAddress(request: RequestAddOrEdit) =
        safeApiCall { api.addAddress(request) }

    suspend fun setAddress(request: RequestSetAddress) =
        safeApiCall {
            api.setAddress(request)
        }
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