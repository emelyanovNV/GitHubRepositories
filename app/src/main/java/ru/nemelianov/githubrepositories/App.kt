package ru.nemelianov.githubrepositories

import android.app.Application
import ru.nemelianov.core_network.di.DaggerNetworkComponent
import ru.nemelianov.githubrepositories.di.DaggerAppComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        DI.appComponent = DaggerAppComponent.builder()
            .appContext(this)
            .build()
        DI.networkComponent = DaggerNetworkComponent.create()
    }
}