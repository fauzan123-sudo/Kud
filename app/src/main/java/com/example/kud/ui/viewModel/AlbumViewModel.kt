package com.example.kud.ui.viewModel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.User
import com.example.kud.data.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(private val repository: AlbumRepository) :
    ViewModel(), LifecycleObserver {

    val readData = repository.readData().asLiveData()

    fun insertData(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun deleteAllUser() {
        viewModelScope.launch {
            repository.deleteAllUser()
        }
    }

    fun updateData() {

    }

//    fun searchData(searchData:String):LiveData<List<User>> {
//        return.repository
//    }


//class AlbumViewModel(application:Application):AndroidViewModel(application) {

//    val readAllData: LiveData<List<User>>
//    private val repository:AlbumRepository
//
//    init{
//        val myDao = MyDatabase.getDatabase(application).myDao()
//        repository = AlbumRepository(myDao)
//        readAllData = repository.readData
//    }
//
//    fun addData(user: User){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addData(user)
//        }
//
//    }
//
//    fun updateData(user: User){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.updateUser(user)
//        }
//    }
//    fun deleteUser(user: User){
//        viewModelScope.launch(Dispatchers.IO){
//            repository.deleteUser(user)
//        }
//    }
//
//    fun deleteAllUser(){
//        viewModelScope.launch {
//            repository.deleteAllUser()
//        }
//    }


}