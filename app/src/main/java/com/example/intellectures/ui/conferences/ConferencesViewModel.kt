package com.example.intellectures.ui.conferences

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.intellectures.model.Conference
import com.example.intellectures.model.Video

class ConferencesViewModel : ViewModel() {
    private val items = MutableLiveData<List<Any>>()
    private var conferences: List<Conference> = ArrayList<Conference>()
    private var currentConferenceId: Int = 0

    fun getItems(): LiveData<List<Any>> = items

    fun setConferences(conferences: List<Conference>) {
        this.conferences = conferences
        items.value = conferences
    }

    fun setVideos(conferenceId: Int, videos: List<Video>) {
        currentConferenceId = conferenceId
        val list = ArrayList<Any>()
        list.addAll(conferences)
        var index = 0
        for (conference in conferences) {
            index++
            if (conference.id == conferenceId) {
                break;
            }
        }
        list.addAll(index, videos)
        items.value = list
    }

    fun getCurrentConferenceId() = currentConferenceId
}
