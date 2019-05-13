package com.example.intellectures.ui.conferences

import com.example.intellectures.di.PerFragment
import com.example.intellectures.model.Conference
import com.example.intellectures.model.Video
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@PerFragment
class ConferencesPresenter @Inject constructor() {
    @Inject
    lateinit var interactor: ConferencesInteractor

    lateinit var view: ConferencesView
    lateinit var router: ConferencesRouter
    lateinit var viewModel: ConferencesViewModel

    fun attach(view: ConferencesView, router: ConferencesRouter, viewModel: ConferencesViewModel) {
        this.view = view
        this.router = router
        this.viewModel = viewModel
    }

    fun reload() {
        interactor.getConferences()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { conferences -> viewModel.setConferences(conferences) }
    }

    fun onConferenceSelected(conference: Conference) {
        interactor.getVideos(conference.id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { videos ->
                viewModel.setVideos(conference.id, videos) }
    }

    fun onVideoSelected(video: Video) {
        router.toVideo(viewModel.getCurrentConferenceId(), video.id)
    }
}