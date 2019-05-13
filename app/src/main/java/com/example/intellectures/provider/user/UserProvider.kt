package com.example.intellectures.provider.user

import com.example.intellectures.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProvider @Inject constructor() {
    val currentUser = User("current")
}