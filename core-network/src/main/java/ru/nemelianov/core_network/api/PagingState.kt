package ru.nemelianov.core_network.api

sealed class PagingState<out T> {
    object Initial : PagingState<Nothing>()
    data class Content<T>(val data: T) : PagingState<T>()
    data class Paging<T>(val availableContent: T) : PagingState<T>()
    data class Error<T>(val availableContent: T?) : PagingState<T>()
}
