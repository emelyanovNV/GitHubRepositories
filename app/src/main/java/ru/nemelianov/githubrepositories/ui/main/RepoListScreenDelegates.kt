package ru.nemelianov.githubrepositories.ui.main

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.nemelianov.githubrepositories.databinding.LiErrorRepoBinding
import ru.nemelianov.githubrepositories.databinding.LiProgressRepoBinding
import ru.nemelianov.githubrepositories.databinding.LiRepoBinding
import ru.nemelianov.githubrepositories.model.base.ListItem
import ru.nemelianov.githubrepositories.model.gh_repository.ErrorRepoItem
import ru.nemelianov.githubrepositories.model.gh_repository.ProgressRepoItem
import ru.nemelianov.githubrepositories.model.gh_repository.RepoItem

object RepoListScreenDelegates {

    fun repoDelegate(onClickItem: (RepoItem) -> Unit, onReadyToLoadMore: (Int) -> Unit) =
        adapterDelegateViewBinding<RepoItem, ListItem, LiRepoBinding>({ layoutInflater, parent ->
            LiRepoBinding.inflate(layoutInflater, parent, false)
        }) {
            binding.root.setOnClickListener {
                onClickItem.invoke(item)
            }
            bind {
                with(binding) {
                    repo = item
                    executePendingBindings()
                }
                onReadyToLoadMore.invoke(adapterPosition)
            }
        }

    fun repoProgressDelegate() =
        adapterDelegateViewBinding<ProgressRepoItem, ListItem, LiProgressRepoBinding>(
            { layoutInflater, parent ->
                LiProgressRepoBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            }
        ) {}

    fun repoErrorDelegate() =
        adapterDelegateViewBinding<ErrorRepoItem, ListItem, LiErrorRepoBinding>(
            { layoutInflater, parent ->
                LiErrorRepoBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            }
        ) {}
}