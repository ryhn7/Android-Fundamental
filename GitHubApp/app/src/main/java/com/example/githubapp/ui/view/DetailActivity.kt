package com.example.githubapp.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.Utils.Companion.setVisibleOrInvisible
import com.example.githubapp.adapter.SectionsPagerAdapter
import com.example.githubapp.databinding.ActivityDetailBinding
import com.example.githubapp.model.DataUser
import com.example.githubapp.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()

    private var username: String? = null
    private var blog: String? = null


    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLE = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        username = intent.extras?.get(EXTRA_USERNAME) as String

        setContentView(binding.root)
        setViewPager()
        setToolbar()

        detailViewModel.user.observe(this) { user ->
            if (user != null) {
                parseUserDetail(user)
                blog = user.blog
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.isError.observe(this) { error ->
            if (error) errorOccurred()
        }

        detailViewModel.callCounter.observe(this) { counter ->
            if (counter < 1) detailViewModel.getUserDetail(username!!)
        }

        binding.tvProfileBlog.setOnClickListener{
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(blog)
            }.also {
                startActivity(it)
            }

        }
    }

    private fun parseUserDetail(user: DataUser) {
        binding.apply {
            tvProfileUsername.text = user.login
            tvRepo.text = user.publicRepos.toString()
            tvFollowers.text = user.followers.toString()
            tvFollowing.text = user.following.toString()

            tvProfileName.setVisibleOrInvisible(user.name)
            tvProfileType.setVisibleOrInvisible(user.type)
            tvProfileDesc.setVisibleOrInvisible(user.bio)
            tvProfileBlog.setVisibleOrInvisible(user.blog)

            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .into(civDetailAvatarProfile)
        }
    }

    private fun setViewPager(){
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabLayout

        viewPager.adapter = SectionsPagerAdapter(this, username!!)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbarDetailProfile)
        binding.collapsingToolbarLayout.isTitleEnabled = false
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = username
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                appBarLayout.visibility = View.GONE
                viewPager.visibility = View.GONE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                appBarLayout.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
            }
        }
    }

    private fun errorOccurred() {
        binding.apply {
            clDetailHeader.visibility = View.GONE
            tabLayout.visibility = View.GONE
            viewPager.visibility = View.GONE
        }
        Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        _binding = null
        username = null
        blog = null

        super.onDestroy()
    }
}