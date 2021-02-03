package ru.nemelianov.githubrepositories.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import ru.nemelianov.githubrepositories.R
import ru.nemelianov.githubrepositories.databinding.FrRepoDetailBinding
import ru.nemelianov.githubrepositories.ui.base.viewBinding

class RepoDetailFragment : Fragment(R.layout.fr_repo_detail) {
    private val binding by viewBinding { FrRepoDetailBinding.bind(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val safeArgs = RepoDetailFragmentArgs.fromBundle(it)
            binding.apply {
                repo = safeArgs.repo
                tvHtmlUrl.setOnClickListener { shareHtmlUrl() }
                executePendingBindings()
            }
        }
    }

    private fun shareHtmlUrl() {
        arguments?.let {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, RepoDetailFragmentArgs.fromBundle(it).repo?.htmlUrl)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) shareHtmlUrl()
        return super.onOptionsItemSelected(item)
    }
}