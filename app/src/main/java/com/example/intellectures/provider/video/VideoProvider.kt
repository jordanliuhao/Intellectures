package com.example.intellectures.provider.conference

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoProvider @Inject constructor() {
    fun getVideos(conferenceId: Int) = MockVideoApi().getVideos(conferenceId)
}