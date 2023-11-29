package com.development.github.ui.users

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.development.github.R
import com.development.github.data.db.UserEntity
import com.development.github.databinding.ItemUsersBinding
import com.development.github.ui.details.UserDetailsActivity

class UsersAdapter : PagingDataAdapter<UserEntity, UsersAdapter.UsersViewHolder>(USER_COMPARATOR){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)
        if(user != null){
            Glide.with(holder.itemView.context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.baseline_broken_image_24)
                .into(holder.binding.imgAvatar)
            holder.binding.txvUsername.text = user.login

            holder.binding.clItem.setOnClickListener {
                val intent = Intent(holder.itemView.context,UserDetailsActivity::class.java)
                intent.putExtra("username",user.login)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    class UsersViewHolder(val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root){}

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean =
                oldItem == newItem
        }
    }
}