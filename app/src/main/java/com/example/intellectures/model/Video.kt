package com.example.intellectures.model

import com.google.gson.annotations.SerializedName

data class Video (
    @SerializedName("id") val id: Int,
    @SerializedName("img") val img: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("desc") val desc: String?,
    @SerializedName("availability") val availability: String?,
    @SerializedName("video") val video: String?
) {
    companion object {
        @JvmStatic
        val LEARN_MORE = "Learn more"
    }

}