package com.example.truecallertest.apiservice

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/blog/life-at-truecaller/life-as-an-android-engineer")
    suspend fun getTrueCallerWebsiteData(): Response<String>

}