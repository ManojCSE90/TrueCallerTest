package com.example.truecallertest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truecallertest.models.NetworkResult
import com.example.truecallertest.repository.TrueCallerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrueCallerViewModel @Inject constructor(private val repository: TrueCallerRepository) : ViewModel() {

    val trueCallerWebsiteData: LiveData<NetworkResult<String>> = repository.trueCallerWebsiteData

    fun getTrueCallerWebsiteData() {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getTrueCallerWebsiteData()
        }
    }
}