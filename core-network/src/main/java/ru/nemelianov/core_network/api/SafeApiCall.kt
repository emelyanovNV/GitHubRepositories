package ru.nemelianov.core_network.api

import retrofit2.HttpException
import retrofit2.Response
import ru.nemelianov.core_network.until.NetworkStatus
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

// Network Messages
const val SOCKET_TIME_OUT_EXCEPTION =
    "Request timed out while trying to connect. Please ensure you have a strong signal and try again."
const val UNKNOWN_NETWORK_EXCEPTION =
    "An unexpected error has occurred. Please check your network connection and try again."
const val CONNECT_EXCEPTION =
    "Could not connect to the server. Please check your internet connection and try again."
const val UNKNOWN_HOST_EXCEPTION =
    "Couldn't connect to the server at the moment. Please try again in a few minutes."

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkStatus<T> {
    try {
        val response = call.invoke()
        if (response.isSuccessful) {
            if (response.body() != null) {
                return NetworkStatus.Success(response.body())
            }
        }
        return NetworkStatus.Error(response.message())
    } catch (e: Exception) {
        return when (e) {
            is ConnectException -> {
                NetworkStatus.Error(CONNECT_EXCEPTION)
            }
            is UnknownHostException -> {
                NetworkStatus.Error(UNKNOWN_HOST_EXCEPTION)
            }
            is SocketTimeoutException -> {
                NetworkStatus.Error(SOCKET_TIME_OUT_EXCEPTION)
            }
            is HttpException -> {
                NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
            }
            else -> {
                NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
            }
        }
    }
}