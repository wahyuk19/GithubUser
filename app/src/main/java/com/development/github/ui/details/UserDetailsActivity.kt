package com.development.github.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
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
    private val userDetailsViewModel: UserDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        with(binding){
            btnBack.setOnClickListener { finish() }
            txvUsername.text = username

            lifecycleScope.launch {
                userDetailsViewModel.userDetail.observe(this@UserDetailsActivity, Observer {resources ->
                    when (resources){
                        is Resource.Loading -> {
                            Log.d("TAG", "onCreate: loading")
                        }
                        is Resource.Success -> {
                            val user = resources.data

                            Glide.with(this@UserDetailsActivity)
                                .load(user?.avatarUrl)
                                .placeholder(R.drawable.baseline_broken_image_24)
                                .into(imgAvatar)

                            val bio = if(user?.bio.isNullOrEmpty()){
                                "-"
                            }else{
                                user?.bio
                            }
                            txvBio.text = "Bio : $bio"
                            txvJoinDate.text = user?.createdAt?.let { convertDate(it) }
                            txvFollowerFollowing.text = "${user?.followers} Followers & ${user?.following} Followings"
                            txvPublicRepos.text = user?.publicRepos.toString()

                        }
                        is Resource.Error -> {

                        }
                    }
                })

                userDetailsViewModel.getUserDetails(username.toString())
            }



        }
    }
}