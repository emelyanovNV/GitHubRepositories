package ru.nemelianov.core_network.model.base

import com.google.gson.annotations.SerializedName
import ru.nemelianov.core_network.model.RepoDto

data class PagedResponse(
    @SerializedName("total_count") val totalCount: Int = 0,
    @SerializedName("items") val items: List<RepoDto> = emptyList()
)