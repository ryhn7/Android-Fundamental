package com.example.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ItemCardBinding
import com.example.githubapp.model.User

class GithubUserResponseAdapter(private val listUserResponse: ArrayList<User>) :
    RecyclerView.Adapter<GithubUserResponseAdapter.GithubUserResponseViewHolder>() {

    class GithubUserResponseViewHolder(var binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GithubUserResponseViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubUserResponseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GithubUserResponseViewHolder, position: Int) {
        val user = listUserResponse[position]

        holder.binding.apply {
            tvUsername.text = user.login
//            use glide to load image
            Glide.with(holder.itemView.context)
                .load(user.avatarUrl)
                .into(imgProfile)
        }
    }

    override fun getItemCount() = listUserResponse.size
}
