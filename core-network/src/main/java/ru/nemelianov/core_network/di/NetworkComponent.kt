package ru.nemelianov.core_network.di

import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nemelianov.core_network.BuildConfig
import ru.nemelianov.core_network.api.GitHubApi
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface NetworkComponent {
    fun api(): GitHubApi
}

@Module
abstract class NetworkModule {
    companion object {
        private const val BASE_URL = "https://api.github.com/"

        @Provides
        @Singleton
        fun provideApi(): GitHubApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level =
                            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            else HttpLoggingInterceptor.Level.NONE
                    })
                    .build()
            )
            .build()
            .create(GitHubApi::class.java)
    }
}