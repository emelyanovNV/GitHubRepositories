package ru.nemelianov.core_network.model

import com.google.gson.annotations.SerializedName

data class OwnerDto(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String?
)
