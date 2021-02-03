package ru.nemelianov.githubrepositories.until

import androidx.annotation.StringRes

interface ResourceProvider {
    fun string(@StringRes id: Int): String
}