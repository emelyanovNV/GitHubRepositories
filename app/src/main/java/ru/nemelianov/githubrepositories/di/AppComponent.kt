package ru.nemelianov.githubrepositories.di

import android.content.Context
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.nemelianov.githubrepositories.until.AndroidResourceProvider
import ru.nemelianov.githubrepositories.until.ResourceProvider
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun resources(): ResourceProvider

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}

@Module
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindResourceProvider(provider: AndroidResourceProvider): ResourceProvider
}