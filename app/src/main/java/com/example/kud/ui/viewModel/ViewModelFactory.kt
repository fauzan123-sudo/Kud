package com.example.kud.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kud.data.repository.RepositorySafe


class ViewModelFactory(private val repository: RepositorySafe) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FakeApiViewModel::class.java) -> FakeApiViewModel(repository)
                    as T
            else -> throw IllegalArgumentException("ViewModelClass  Not Found")
        }
    }
}