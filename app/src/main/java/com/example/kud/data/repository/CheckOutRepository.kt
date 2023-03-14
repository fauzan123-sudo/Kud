package com.example.kud.data.repository

import androidx.lifecycle.LiveData
import com.example.kud.data.db.CheckOutDao
import com.example.kud.data.model.CheckOut
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckOutRepository @Inject constructor(private val dao: CheckOutDao) {

    fun readData(): LiveData<List<CheckOut>> {
        return dao.getAllData()
    }

    suspend fun insertItem(checkOut: CheckOut) {
        dao.insertItems(checkOut)
    }


//    val readData: LiveData<List<User>> = myDao.getAllData()

    suspend fun updateItem(checkOut: CheckOut){
        dao.updateItem(checkOut)
    }

    suspend fun deleteItem(checkOut: CheckOut){
        dao.delete(checkOut)
    }

    suspend fun deleteAllItem(){
        dao.deleteAllItem()
    }
}