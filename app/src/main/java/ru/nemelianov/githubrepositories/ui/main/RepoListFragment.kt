package ru.nemelianov.githubrepositories.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nemelianov.githubrepositories.R
import ru.nemelianov.githubrepositories.databinding.FrRepoListBinding
import ru.nemelianov.githubrepositories.ui.viewmodels.main.RepoListScreenViewModel
import ru.nemelianov.githubrepositories.utils.viewBinding

@AndroidEntryPoint
class RepoListFragment : Fragment(R.layout.fr_repo_list) {
    private val binding by viewBinding { FrRepoListBinding.bind(it) }
    private val viewModel: RepoListScreenViewModel by viewModels()

    private val adapter by lazy {
        RepoListAdapter(onClickItem = {
            findNavController().navigate(RepoListFragmentDirections.actionListToDetail(it))
        }, onReadyToLoadMore = viewModel::readyToLoadMore)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            vm = viewModel
//            srLayout.setOnRefreshListener {
//                viewModel.loadData()
//            }
            rvRepos.adapter = adapter
            viewModel.data.observe(viewLifecycleOwner, {
                adapter.items = it
//                srLayout.isRefreshing = false
            })
            executePendingBindings()
        }
    }
}