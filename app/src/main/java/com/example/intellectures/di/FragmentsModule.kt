package com.example.intellectures.di

import com.example.intellectures.ui.conferences.ConferencesFragment
import com.example.intellectures.ui.conferences.ConferencesFragmentModule
import com.example.intellectures.ui.video.VideoFragment
import com.example.intellectures.ui.video.VideoFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector(modules = [ConferencesFragmentModule::class])
    @PerFragment
    abstract fun conferencesFragment(): ConferencesFragment

    @ContributesAndroidInjector(modules = [VideoFragmentModule::class])
    @PerFragment
    abstract fun videoFragment(): VideoFragment
}