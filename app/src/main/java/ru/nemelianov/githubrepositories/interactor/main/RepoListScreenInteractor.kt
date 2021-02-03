package ru.nemelianov.githubrepositories.interactor.main

import kotlinx.coroutines.flow.Flow
import ru.nemelianov.githubrepositories.model.base.ListItem

interface RepoListScreenInteractor {
    fun data(): Flow<List<ListItem>>

    suspend fun initList()

    suspend fun tryToLoadMore(index: Int)
}