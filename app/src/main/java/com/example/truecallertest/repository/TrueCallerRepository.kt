package com.example.truecallertest.repository

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.truecallertest.R
import com.example.truecallertest.apiservice.ApiService
import com.example.truecallertest.models.NetworkResult
import javax.inject.Inject

class TrueCallerRepository @Inject constructor(
    private val resources: Resources,
    private val apiService: ApiService
) {

    private val _trueCallerWebsiteData = MutableLiveData<NetworkResult<String>>()
    val trueCallerWebsiteData: LiveData<NetworkResult<String>> = _trueCallerWebsiteData

    suspend fun getTrueCallerWebsiteData() {

        try {

            //show loader while getting response from server
            _trueCallerWebsiteData.postValue(NetworkResult.Loading())

            val response = apiService.getTrueCallerWebsiteData()

            if (response.isSuccessful && response.body() != null) {
                _trueCallerWebsiteData.postValue(NetworkResult.Success(response.body()))
            } else {
                _trueCallerWebsiteData.postValue(
                    NetworkResult.Failure(
                        response?.errorBody()?.string()
                            ?: resources.getString(R.string.something_went_wrong)
                    )
                )
            }
        } catch (e: Exception) {
            _trueCallerWebsiteData.postValue(
                NetworkResult.Failure(
                    e.message ?: resources.getString(R.string.something_went_wrong)
                )
            )

        }
    }
}