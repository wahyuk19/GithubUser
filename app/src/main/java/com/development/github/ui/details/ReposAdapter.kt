package com.development.github.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.github.data.model.ReposItem
import com.development.github.databinding.ItemReposBinding
import com.development.github.utils.ReposDiffCallback
import com.development.github.utils.convertDateRepos

class ReposAdapter: RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    private val listRepos = ArrayList<ReposItem>()

    fun setListRepos(listRepos: List<ReposItem>){
        val diffCallback = ReposDiffCallback(this.listRepos,listRepos)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listRepos.clear()
        this.listRepos.addAll(listRepos)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReposAdapter.ReposViewHolder {
        val binding = ItemReposBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReposViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReposAdapter.ReposViewHolder, position: Int) {
        holder.bind(listRepos[position])
    }

    override fun getItemCount(): Int = listRepos.size

    inner class ReposViewHolder(private val binding: ItemReposBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(repos: ReposItem){
            with(binding){
                txvRepoName.text = repos.name
                txvCreatedDate.text = convertDateRepos(repos.created_at)
                txvLanguage.text = repos.language
            }
        }
    }

}