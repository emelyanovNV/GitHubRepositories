package ru.nemelianov.githubrepositories.repository

import kotlinx.coroutines.flow.Flow
import ru.nemelianov.core_network.api.PagingState
import ru.nemelianov.core_network.api.RepoRemoteDataSource
import ru.nemelianov.core_network.api.params.GithubApiParams
import ru.nemelianov.core_network.model.RepoDto
import ru.nemelianov.githubrepositories.R
import ru.nemelianov.githubrepositories.until.ResourceProvider
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val dataSource: RepoRemoteDataSource,
    private val resources: ResourceProvider
) : RepoRepository {

    override fun data(): Flow<PagingState<List<RepoDto>>> = dataSource.observe()

    override suspend fun init() {
        dataSource.initialLoading(
            GithubApiParams(
                query = resources.string(R.string.query_search),
                sort = resources.string(R.string.sort_search)
            )
        )
    }

    override suspend fun loadMore(index: Int) {
        dataSource.loadMore(index)
    }
}