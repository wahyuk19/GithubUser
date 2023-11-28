package com.development.github.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.development.github.R
import com.development.github.data.Resource
import com.development.github.databinding.ActivityUserDetailsBinding
import com.development.github.utils.convertDate
import com.development.github.viewmodel.UserDetailsViewModel
import com.development.github.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserDetailsBinding
    private lateinit var adapter: ReposAdapter
    private val userDetailsViewModel: UserDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        adapter = ReposAdapter()

        with(binding){
            btnBack.setOnClickListener { finish() }
            txvUsername.text = username

            lifecycleScope.launch {
                userDetailsViewModel.userDetail.observe(this@UserDetailsActivity) { resources ->
                    when (resources) {
                        is Resource.Loading -> {
                            Log.d("TAG", "onCreate: loading")
                        }

                        is Resource.Success -> {
                            val user = resources.data

                            Glide.with(this@UserDetailsActivity)
                                .load(user?.avatarUrl)
                                .placeholder(R.drawable.baseline_broken_image_24)
                                .into(imgAvatar)

                            val bio = if (user?.bio.isNullOrEmpty()) {
                                "-"
                            } else {
                                user?.bio
                            }
                            txvBio.text = "Bio : $bio"
                            txvJoinDate.text =
                                "Join Date : ${user?.createdAt?.let { convertDate(it) }}"
                            txvFollowerFollowing.text =
                                "${user?.followers} Followers & ${user?.following} Followings"
                            txvPublicRepos.text = user?.publicRepos.toString()

                        }

                        is Resource.Error -> {
                            Toast.makeText(
                                this@UserDetailsActivity,
                                "Error retrieve data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                userDetailsViewModel.getUserDetails(username.toString())

                rvRepos.layoutManager = LinearLayoutManager(this@UserDetailsActivity)
                rvRepos.adapter = adapter

                userDetailsViewModel.userRepos.observe(this@UserDetailsActivity){ res ->
                    when (res) {
                        is Resource.Loading -> {
                            Log.d("TAG", "onCreate: loading")
                        }

                        is Resource.Success -> {
                            res.data?.let { adapter.setListRepos(it) }
                        }

                        is Resource.Error -> {
                            Toast.makeText(
                                this@UserDetailsActivity,
                                "Error retrieve data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }

                userDetailsViewModel.getRepos(username.toString())
            }



        }
    }
}