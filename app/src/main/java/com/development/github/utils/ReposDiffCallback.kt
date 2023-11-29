package com.development.github.utils

import androidx.recyclerview.widget.DiffUtil
import com.development.github.data.model.ReposItem

class ReposDiffCallback(private val oldReposList: List<ReposItem>, private val newReposList: List<ReposItem>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldReposList.size

    override fun getNewListSize(): Int = newReposList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldReposList[oldItemPosition].id == newReposList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepos = oldReposList[oldItemPosition]
        val newRepos = newReposList[newItemPosition]
        return oldRepos.name == newRepos.name && oldRepos.created_at == newRepos.created_at && oldRepos.language == newRepos.language
    }

}