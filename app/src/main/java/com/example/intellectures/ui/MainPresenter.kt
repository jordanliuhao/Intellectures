package com.example.intellectures.ui

import com.example.intellectures.ui.MainInteractor
import javax.inject.Inject

class MainPresenter @Inject constructor() {
    @Inject
    lateinit var interactor: MainInteractor

    private lateinit var view: MainView

    fun attach(view: MainView) {
        this.view = view
    }
}