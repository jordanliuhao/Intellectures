package com.example.intellectures.ui.video

import com.example.intellectures.di.PerFragment
import com.example.intellectures.provider.conference.VideoProvider
import javax.inject.Inject


@PerFragment
class VideoInteractor @Inject constructor() {
    @Inject
    lateinit var videoProvider: VideoProvider

    fun getVideos(conferenceId: Int) = videoProvider.getVideos(conferenceId)
}
