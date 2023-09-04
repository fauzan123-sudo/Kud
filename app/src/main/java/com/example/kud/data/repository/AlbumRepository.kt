package com.example.kud.data.repository

//import com.example.kud.data.db.MyDao
import com.example.kud.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


//class AlbumRepository @Inject constructor(private val provideDao: MyDao) {
//
//    fun readData(): Flow<List<User>> {
//        return provideDao.getAllData()
//    }
//
//    suspend fun insertData(user: User) {
//        provideDao.InsertAlbum(user)
//    }
//
//
////    val readData: LiveData<List<User>> = myDao.getAllData()
//
//    suspend fun updateUser(user: User){
//        provideDao.updateUser(user)
//    }
//
//    suspend fun deleteUser(user: User){
//        provideDao.delete(user)
//    }
//
//    suspend fun deleteAllUser(){
//        provideDao.deleteAllUser()
//    }
//
//}