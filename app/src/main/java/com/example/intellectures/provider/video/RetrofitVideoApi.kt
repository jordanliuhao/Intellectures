package com.example.intellectures.provider.conference

import com.example.intellectures.model.Video
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitVideoApi {
    @GET("/videos?conference={conferenceId}")
    fun getVideos(@Path("conferenceId") conferenceId: Int): Single<List<Video>>
}