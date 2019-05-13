package com.example.intellectures.ui.conferences

import com.example.intellectures.di.PerFragment
import com.example.intellectures.model.Conference
import com.example.intellectures.provider.conference.ConferenceProvider
import com.example.intellectures.provider.conference.VideoProvider
import com.example.intellectures.provider.user.UserProvider
import io.reactivex.Single
import javax.inject.Inject

@PerFragment
class ConferencesInteractor @Inject constructor() {
    @Inject
    lateinit var conferenceProvider: ConferenceProvider
    @Inject
    lateinit var videoProvider: VideoProvider

    @Inject
    lateinit var userProvider: UserProvider

    fun getConferences() = conferenceProvider.getConferences(userProvider.currentUser.id)

    fun getVideos(conferenceId: Int) = videoProvider.getVideos(conferenceId)
}