package com.example.truecallertest.models

sealed class NetworkResult<T>(val data: T? = null, val errorMsg: String? = null) {

    class Success<T>(data: T?) : NetworkResult<T>(data = data)
    class Failure<T>(errorMsg: String?) : NetworkResult<T>(errorMsg = errorMsg)
    class Loading<T> : NetworkResult<T>()

}