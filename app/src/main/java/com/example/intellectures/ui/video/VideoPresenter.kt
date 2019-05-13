package com.example.intellectures.ui.video

import com.example.intellectures.di.PerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@PerFragment
class VideoPresenter @Inject constructor() {
    @Inject
    lateinit var interactor: VideoInteractor

    lateinit var view: VideoView
    lateinit var router: VideoRouter
    lateinit var viewModel: VideoViewModel

    fun attach(view: VideoView, router: VideoRouter, viewModel: VideoViewModel) {
        this.view = view
        this.router = router
        this.viewModel = viewModel
    }

    fun reload(conferenceId: Int, videoId: Int) {
        interactor.getVideos(videoId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { videos ->
                for (video in videos) {
                    if (video.id == videoId) {
                        viewModel.setVideo(video)
                        break
                    }
                }
            }
    }
}