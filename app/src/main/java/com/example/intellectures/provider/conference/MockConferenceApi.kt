package com.example.intellectures.provider.conference

import com.example.intellectures.model.Conference
import com.google.gson.Gson
import io.reactivex.Single

class MockConferenceApi: RetrofitConferenceApi {
    val getConferencesResponse =
        """
[
    {"id": 1, "city": "Paris", "dates": "Aug 25 - Sept 01, 2019"},
    {"id": 2, "city": "Amsterdam", "dates": "Oct 10 - Oct - 15, 2019"}
]
        """.trimIndent()

    override fun getConferences(userId: String) = Single.just(
            Gson().run {
                fromJson(getConferencesResponse, Array<Conference>::class.java).toList()
            }
        )
}