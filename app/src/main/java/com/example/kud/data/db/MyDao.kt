package com.example.kud.data.db

//import androidx.lifecycle.LiveData
//import androidx.room.*
//import com.example.kud.data.model.User
//import kotlinx.coroutines.flow.Flow

//@Dao
//interface MyDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun InsertAlbum(user : User)
//
//    @Query("Select * From users")
//    fun getAllData(): Flow<List<User>>
//
//    @Update
//    suspend fun updateUser(user: User)
//
//    @Query("Delete from users")
//    suspend fun deleteAllUser()
//
//    @Delete
//    suspend fun delete(user: User)
//
//    @Query("SELECT * FROM users WHERE firstName LIKE :searchQuery OR lastName LIKE :searchQuery")
//    fun searchDatabase(searchQuery: String): Flow<List<User>>
//
//}