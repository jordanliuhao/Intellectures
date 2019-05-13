package com.example.intellectures.model

import com.google.gson.annotations.SerializedName

data class Conference (
    @SerializedName("id") val id: Int,
    @SerializedName("city") val city: String?,
    @SerializedName("dates") val dates: String?
)