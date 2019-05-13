package com.example.intellectures.ui.video

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.intellectures.model.Video

class VideoViewModel : ViewModel() {
    private val video = MutableLiveData<Video>()

    fun getVideo(): LiveData<Video> = video

    fun setVideo(video: Video) {
        this.video.value = video
    }
}
