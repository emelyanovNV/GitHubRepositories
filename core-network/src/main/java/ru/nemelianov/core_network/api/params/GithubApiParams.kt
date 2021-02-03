package ru.nemelianov.core_network.api.params

data class GithubApiParams(
    val query: String? = null,
    val sort: String? = null
) {
    fun toMap(): Map<String, String> = mutableMapOf<String, String>().apply {
        query?.let { put("q", it) }
        sort?.let { put("sort", it) }
    }
}
