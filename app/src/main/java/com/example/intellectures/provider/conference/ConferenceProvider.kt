package com.example.intellectures.provider.conference

import com.example.intellectures.model.Conference
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConferenceProvider @Inject constructor() {
    fun getConferences(userId: String) = MockConferenceApi().getConferences(userId)
}