package com.example.intellectures.di

import com.example.intellectures.App
import com.example.intellectures.provider.conference.ConferenceProvider
import com.example.intellectures.provider.conference.VideoProvider
import com.example.intellectures.provider.user.UserProvider
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component (modules = [
    AppModule::class,
    AndroidSupportInjectionModule::class,
    ActivitiesModule::class,
    FragmentsModule::class
])
interface AppComponent: AndroidInjector<App> {
    fun conferenceProvider(): ConferenceProvider
    fun videoProvider(): VideoProvider
    fun userProvider(): UserProvider
}