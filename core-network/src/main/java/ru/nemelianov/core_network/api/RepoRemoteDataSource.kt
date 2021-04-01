package ru.nemelianov.core_network.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.nemelianov.core_network.api.params.GithubApiParams
import ru.nemelianov.core_network.model.RepoDto
import ru.nemelianov.core_network.until.NetworkStatus

class RepoRemoteDataSource(
    private val api: GitHubApi
) {
    private val itemsFlow =
            MutableStateFlow<PagingState<List<RepoDto>>>(PagingState.Initial)
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
                    itemsFlow.value = PagingState.Content(it.items)
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
        val cache = itemsFlow.value
        if (totalCount < page * DEFAULT_PAGE_SIZE) return
        if (cache is PagingState.Content && candidatePosition == cache.data.size - 1) {
            page += 1
            itemsFlow.value = PagingState.Paging(cache.data)
            when (val response = safeApiCall { api.searchRepo(params.applyPagingParams(page)) }) {
                is NetworkStatus.Success -> {
                    response.data?.let { itemsFlow.value = PagingState.Content(cache.data.plus(it.items)) }
                }
                else -> {
                    sendErrorState()
                }
            }
        }
    }

    @Synchronized
    private fun sendErrorState() {
        when (val cache = itemsFlow.value) {
            is PagingState.Content -> itemsFlow.value = PagingState.Error(cache.data)
            is PagingState.Paging -> itemsFlow.value = PagingState.Error(cache.availableContent)
            else -> itemsFlow.value = PagingState.Error(null)
        }
    }

    fun observe(): Flow<PagingState<List<RepoDto>>> = itemsFlow

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