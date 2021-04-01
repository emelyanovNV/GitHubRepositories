package ru.nemelianov.core_network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.nemelianov.core_network.model.base.PagedResponse

interface GitHubApi {
    @GET("search/repositories")
    suspend fun searchRepo(@QueryMap params: Map<String, String>): Response<PagedResponse>
}