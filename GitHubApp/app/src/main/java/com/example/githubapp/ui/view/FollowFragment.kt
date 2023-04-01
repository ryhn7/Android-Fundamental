package com.example.githubapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.adapter.GithubUserResponseAdapter
import com.example.githubapp.databinding.FragmentFollowBinding
import com.example.githubapp.model.User
import com.example.githubapp.ui.viewmodel.FollowViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val followViewModel: FollowViewModel by viewModels()

    companion object {
        const val ARGS_USERNAME = "username"
        const val ARG_POSITION = "position"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ARG_POSITION, 0) ?: 0
        if (position == 1) {
            followViewModel.followers.observe(viewLifecycleOwner) {
                if (it == null) {
                    val username = arguments?.getString(ARGS_USERNAME) ?: ""
                    followViewModel.getUserFollowers(username)
                } else {
                    showFollowers(it)
                }
            }
        } else {
            followViewModel.following.observe(viewLifecycleOwner) {
                if (it == null) {
                    val username = arguments?.getString(ARGS_USERNAME) ?: ""
                    followViewModel.getUserFollowing(username)
                } else {
                    showFollowing(it)
                }
            }
        }
    }

    private fun showFollowers(users: ArrayList<User>) {
        if (users.size > 0) {
            val linearLayoutManager = LinearLayoutManager(activity)
            val githubUserResponseAdapter = GithubUserResponseAdapter(users)

            binding.rvUsers.apply {
                layoutManager = linearLayoutManager
                adapter = githubUserResponseAdapter
                setHasFixedSize(true)
            }

            githubUserResponseAdapter.setOnItemClickCallback(object :
                GithubUserResponseAdapter.OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    goToDetailUser(user)
                }
            })
        } else {
            binding.tvStatus.visibility = View.VISIBLE
        }
    }

    private fun showFollowing(users: ArrayList<User>) {
        if (users.size > 0) {
            val linearLayoutManager = LinearLayoutManager(activity)
            val githubUserResponseAdapter = GithubUserResponseAdapter(users)

            binding.rvUsers.apply {
                layoutManager = linearLayoutManager
                adapter = githubUserResponseAdapter
                setHasFixedSize(true)
            }

            githubUserResponseAdapter.setOnItemClickCallback(object : GithubUserResponseAdapter.OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    goToDetailUser(user)
                }
            })
        } else {
            binding.tvStatus.visibility = View.VISIBLE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun goToDetailUser(user: User) {
        Intent(activity, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_USERNAME, user.login)
        }.also { startActivity(it) }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}