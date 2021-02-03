package ru.nemelianov.githubrepositories.ui.viewmodels.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import ru.nemelianov.core_network.api.GitHubApi
import ru.nemelianov.githubrepositories.DI
import ru.nemelianov.githubrepositories.di.ScreenScope
import ru.nemelianov.githubrepositories.di.ViewModelFactory
import ru.nemelianov.githubrepositories.di.ViewModelKey
import ru.nemelianov.githubrepositories.interactor.main.RepoListScreenInteractor
import ru.nemelianov.githubrepositories.interactor.main.RepoListScreenInteractorImpl
import ru.nemelianov.githubrepositories.until.ResourceProvider

@Component(modules = [RepoListScreenModule::class])
@ScreenScope
interface RepoListScreenComponent {
    fun viewModelFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun resources(resourceProvider: ResourceProvider): Builder

        @BindsInstance
        fun api(api: GitHubApi): Builder

        fun build(): RepoListScreenComponent
    }

    companion object {
        fun create() = with(DI.appComponent) {
            DaggerRepoListScreenComponent.builder()
                .resources(resources())
                .api(DI.networkComponent.api())
                .build()
        }
    }
}

@Module
abstract class RepoListScreenModule {
    @Binds
    @IntoMap
    @ViewModelKey(RepoListScreenViewModel::class)
    abstract fun repoListScreenViewModel(viewModel: RepoListScreenViewModel): ViewModel

    @Binds
    @ScreenScope
    abstract fun repoListScreenInteractor(interactor: RepoListScreenInteractorImpl): RepoListScreenInteractor
}