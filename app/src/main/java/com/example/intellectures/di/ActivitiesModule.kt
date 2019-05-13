package com.example.intellectures.di

import com.example.intellectures.ui.MainActivity
import com.example.intellectures.ui.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    @PerActivity
    abstract fun mainActivity(): MainActivity
}