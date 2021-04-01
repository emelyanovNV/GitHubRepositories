package ru.nemelianov.githubrepositories.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import ru.nemelianov.core_network.api.RepoRemoteDataSource
import ru.nemelianov.githubrepositories.interactor.main.RepoListScreenInteractor
import ru.nemelianov.githubrepositories.interactor.main.RepoListScreenInteractorImpl
import ru.nemelianov.githubrepositories.repository.RepoRepository
import ru.nemelianov.githubrepositories.repository.RepoRepositoryImpl
import ru.nemelianov.githubrepositories.utils.AndroidResourceProvider
import ru.nemelianov.githubrepositories.utils.ResourceProvider


@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    fun bindResourceProvider(@ApplicationContext appContext: Context): ResourceProvider =
        AndroidResourceProvider(appContext)

    @Provides
    @ViewModelScoped
    fun provideRepoRepository(
        dataSource: RepoRemoteDataSource,
        resources: ResourceProvider
    ): RepoRepository =
        RepoRepositoryImpl(dataSource, resources)

    @Provides
    @ViewModelScoped
    fun repoListScreenInteractor(
        repository: RepoRepository
    ): RepoListScreenInteractor =
        RepoListScreenInteractorImpl(repository)
}