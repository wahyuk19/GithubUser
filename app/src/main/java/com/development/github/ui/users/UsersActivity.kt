package com.development.github.ui.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.github.R
import com.development.github.databinding.ActivityUsersBinding
import com.development.github.utils.NotificationPermissionDialog
import com.development.github.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    private lateinit var adapter: UsersAdapter
    private val usersViewModel: UsersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter()

        with(binding){
            checkAndRequestNotificationPermission()

            rvUsers.layoutManager = LinearLayoutManager(this@UsersActivity)
            rvUsers.adapter = adapter

            getAllUsers()

            edtSearch.addTextChangedListener{ s ->
                val isSearching = if(s.toString().isNotEmpty()){
                    lifecycleScope.launch {
                        usersViewModel.searchUsers(s.toString()).collectLatest {pagingData ->
                            adapter.submitData(pagingData)
                        }
                    }

                    true
                }else{
                    getAllUsers()
                    false
                }
                checkEndButton(isSearching)
            }
        }
    }

    private fun checkAndRequestNotificationPermission() {
        if (!NotificationPermissionDialog.isNotificationPermissionGranted(this)) {
            NotificationPermissionDialog.showPermissionDialog(this)
        }
    }

    private fun getAllUsers(){
        lifecycleScope.launch {
            usersViewModel.getUsers().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun checkEndButton(isSearching: Boolean) {
        if(isSearching){
            binding.btnClear.setBackgroundResource(R.drawable.baseline_clear_24)
            binding.btnClear.setOnClickListener {
                binding.edtSearch.setText("")
            }
        }else{
            binding.btnClear.setBackgroundResource(R.drawable.baseline_search_24)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.btnClear.setBackgroundResource(R.drawable.baseline_search_24)
    }
}