package ru.nemelianov.githubrepositories.model.gh_repository

import ru.nemelianov.githubrepositories.model.base.ListItem
import java.io.Serializable

data class RepoItem(
    val id: Long,
    val name: String,
    val description: String?,
    val htmlUrl: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String?
) : ListItem, Serializable {
    override val itemId = id
}
