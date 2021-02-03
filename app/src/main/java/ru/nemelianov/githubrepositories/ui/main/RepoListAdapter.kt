package ru.nemelianov.githubrepositories.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.nemelianov.githubrepositories.model.base.ListItem
import ru.nemelianov.githubrepositories.model.gh_repository.RepoItem

class RepoListAdapter(
    onClickItem: (RepoItem) -> Unit,
    onReadyToLoadMore: (Int) -> Unit
) : AsyncListDifferDelegationAdapter<ListItem>(object : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.equals(newItem)
    }
}) {
    init {
        setHasStableIds(true)
        delegatesManager
            .addDelegate(RepoListScreenDelegates.repoDelegate(onClickItem, onReadyToLoadMore))
            .addDelegate(RepoListScreenDelegates.repoProgressDelegate())
            .addDelegate(RepoListScreenDelegates.repoErrorDelegate())
    }

    override fun getItemId(position: Int): Long {
        return items[position].itemId
    }
}