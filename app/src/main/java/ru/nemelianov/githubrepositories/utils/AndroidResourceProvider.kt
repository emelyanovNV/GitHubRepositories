package ru.nemelianov.githubrepositories.utils

import android.content.Context

class AndroidResourceProvider(
    private val context: Context
) : ResourceProvider {
    override fun string(id: Int): String = context.resources.getString(id)
}