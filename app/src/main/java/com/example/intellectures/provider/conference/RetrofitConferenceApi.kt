package com.example.intellectures.provider.conference

import com.example.intellectures.model.Conference
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitConferenceApi {
    @GET("/conferences?userid={userId}")
    fun getConferences(@Path("userId") userId: String): Single<List<Conference>>
}