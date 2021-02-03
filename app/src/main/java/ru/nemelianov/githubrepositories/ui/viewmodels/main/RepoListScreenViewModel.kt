package ru.nemelianov.githubrepositories.ui.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.nemelianov.githubrepositories.interactor.main.RepoListScreenInteractor
import ru.nemelianov.githubrepositories.model.base.ListItem
import ru.nemelianov.githubrepositories.ui.viewmodels.base.BaseViewModel
import javax.inject.Inject

class RepoListScreenViewModel @Inject constructor(
    private val interactor: RepoListScreenInteractor
) : BaseViewModel() {
    private val _data = MutableLiveData<List<ListItem>>()
    val data: LiveData<List<ListItem>> = _data

    init {
        viewModelScope.launch(Dispatchers.Main) {
            interactor.data().collect { _data.value = it }
        }
        initList()
    }

    fun initList() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.initList()
        }
    }

    fun readyToLoadMore(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.tryToLoadMore(index)
        }
    }
}