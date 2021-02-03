package ru.nemelianov.githubrepositories.repository

import kotlinx.coroutines.flow.Flow
import ru.nemelianov.core_network.api.PagingState
import ru.nemelianov.core_network.model.RepoDto

interface RepoRepository {
    fun data(): Flow<PagingState<List<RepoDto>>>

    suspend fun init()

    suspend fun loadMore(index: Int)
}