package com.example.kud.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kud.data.model.profile.ProfileResponse
import com.example.kud.data.repository.ProfileRepository
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) :
    ViewModel() {

    private val _profileResponseLiveData = MutableLiveData<NetworkResult<ProfileResponse>>()
    val profileResponseLiveData: LiveData<NetworkResult<ProfileResponse>>
        get() = _profileResponseLiveData

    fun requestProfileUser() {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _profileResponseLiveData.postValue(NetworkResult.Loading())
                _profileResponseLiveData.postValue(repository.profileUser())
            } else
                _profileResponseLiveData.postValue(NetworkResult.Error("No Internet Connection"))
        }

    }
}