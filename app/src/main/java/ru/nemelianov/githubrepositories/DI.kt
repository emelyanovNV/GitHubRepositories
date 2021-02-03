package ru.nemelianov.githubrepositories

import ru.nemelianov.core_network.di.NetworkComponent
import ru.nemelianov.githubrepositories.di.AppComponent

object DI {
    lateinit var appComponent: AppComponent
        internal set
    lateinit var networkComponent: NetworkComponent
        internal set
}