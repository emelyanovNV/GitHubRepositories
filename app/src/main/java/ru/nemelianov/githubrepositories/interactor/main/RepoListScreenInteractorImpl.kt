package ru.nemelianov.githubrepositories.interactor.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.nemelianov.core_network.api.PagingState
import ru.nemelianov.core_network.model.RepoDto
import ru.nemelianov.githubrepositories.model.base.ListItem
import ru.nemelianov.githubrepositories.model.gh_repository.ErrorRepoItem
import ru.nemelianov.githubrepositories.model.gh_repository.ProgressRepoItem
import ru.nemelianov.githubrepositories.model.gh_repository.RepoItem
import ru.nemelianov.githubrepositories.repository.RepoRepository

class RepoListScreenInteractorImpl(
    private val repoRepository: RepoRepository
) : RepoListScreenInteractor {

    override fun data(): Flow<List<ListItem>> = repoRepository.data().map { mapToType(it) }

    override suspend fun initList() {
        repoRepository.init()
    }

    override suspend fun tryToLoadMore(index: Int) {
        repoRepository.loadMore(index)
    }

    private fun mapToType(dataState: PagingState<List<RepoDto>>): List<ListItem> =
        when (dataState) {
            is PagingState.Initial -> {
                IntRange(1, 4).map { ProgressRepoItem }
            }
            is PagingState.Content -> {
                dataState.data.map {
                    RepoItem(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        htmlUrl = it.htmlUrl,
                        ownerLogin = it.owner.login,
                        ownerAvatarUrl = it.owner.avatarUrl
                    )
                }
            }
            is PagingState.Paging -> {
                dataState.availableContent.map {
                    RepoItem(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        htmlUrl = it.htmlUrl,
                        ownerLogin = it.owner.login,
                        ownerAvatarUrl = it.owner.avatarUrl
                    )
                }.plus(ProgressRepoItem)
            }
            is PagingState.Error -> {
                (dataState.availableContent?.map {
                    RepoItem(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        htmlUrl = it.htmlUrl,
                        ownerLogin = it.owner.login,
                        ownerAvatarUrl = it.owner.avatarUrl
                    )
                } ?: arrayListOf()).plus(ErrorRepoItem)
            }
            else -> throw IllegalStateException("Wrong paging state $dataState")
        }
}