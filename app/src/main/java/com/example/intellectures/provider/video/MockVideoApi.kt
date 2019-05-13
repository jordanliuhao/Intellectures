package com.example.intellectures.provider.conference

import com.example.intellectures.model.Video
import com.google.gson.Gson
import io.reactivex.Single

class MockVideoApi: RetrofitVideoApi {
    val getVideosResponse =
        """
[
    {"id": 1, "img": "https://www.intellectures.com/theme/clean/pix/home/explainervideo-poster.jpg", "title": "Interpretation of Medical Literature", "desc": "The RIT", "availability": "Learn more", "video": "https://lecturelibrary.streaming.mediaservices.windows.net/f15a903a-c649-44dc-a4e4-786bab18bcd4/Explainer Video_revised.ism/manifest"},
    {"id": 2, "img": "https://www.intellectures.com/theme/clean/pix/home/explainervideo-poster.jpg", "title": "Orth Hypo", "desc": "Structuring Diagnosis", "availability": "August 27", "video": "https://lecturelibrary.streaming.mediaservices.windows.net/f15a903a-c649-44dc-a4e4-786bab18bcd4/Explainer Video_revised.ism/manifest"},
    {"id": 3, "img": "https://www.intellectures.com/theme/clean/pix/home/explainervideo-poster.jpg", "title": "Severe Asthma", "desc": "Diagnosis, Control, and Management", "availability": "August 28", "video": "https://lecturelibrary.streaming.mediaservices.windows.net/f15a903a-c649-44dc-a4e4-786bab18bcd4/Explainer Video_revised.ism/manifest"}
]
        """.trimIndent()

    override fun getVideos(conferenceId: Int) = Single.just(
            Gson().run {
                fromJson(getVideosResponse, Array<Video>::class.java).toList()
            }
        )
}