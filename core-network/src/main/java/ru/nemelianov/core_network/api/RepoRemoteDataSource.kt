package ru.nemelianov.core_network.api

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import ru.nemelianov.core_network.api.params.GithubApiParams
import ru.nemelianov.core_network.model.RepoDto
import ru.nemelianov.core_network.until.NetworkStatus
import javax.inject.Inject

class RepoRemoteDataSource @Inject constructor(
    private val api: GitHubApi
) {
    private val channel =
        ConflatedBroadcastChannel<PagingState<List<RepoDto>>>(PagingState.Initial)
    private var params: GithubApiParams? = null
    private var totalCount = 0
    private var page = 1

    @Synchronized
    suspend fun initialLoading(params: GithubApiParams) {
        this.page = 1
        val response = safeApiCall { api.searchRepo(params.applyPagingParams()) }
        this.params = params
        when (response) {
            is NetworkStatus.Success -> {
                response.data?.let {
                    totalCount = it.totalCount
                    channel.send(PagingState.Content(it.items))
                }
            }
            else -> {
                sendErrorState()
            }
        }
    }

    @Synchronized
    suspend fun loadMore(candidatePosition: Int) {
        val params = this.params ?: return
        val cache = channel.value
        if (totalCount < page * DEFAULT_PAGE_SIZE) return
        if (cache is PagingState.Content && candidatePosition == cache.data.size - 1) {
            page += 1
            channel.send(PagingState.Paging(cache.data))
            when (val response = safeApiCall { api.searchRepo(params.applyPagingParams(page)) }) {
                is NetworkStatus.Success -> {
                    response.data?.let { channel.send(PagingState.Content(cache.data.plus(it.items))) }
                }
                else -> {
                    sendErrorState()
                }
            }
        }
    }

    @Synchronized
    private suspend fun sendErrorState() {
        when (val cache = channel.value) {
            is PagingState.Content -> channel.send(PagingState.Error(cache.data))
            is PagingState.Paging -> channel.send(PagingState.Error(cache.availableContent))
            else -> channel.send(PagingState.Error(null))
        }
    }

    fun observe(): Flow<PagingState<List<RepoDto>>> = channel.asFlow()

    private fun GithubApiParams.applyPagingParams(page: Int = 1): Map<String, String> = toMap()
        .toMutableMap()
        .apply {
            put("page", page.toString())
            put("per_page", DEFAULT_PAGE_SIZE.toString())
        }

    private companion object {
        const val DEFAULT_PAGE_SIZE = 15
    }
}