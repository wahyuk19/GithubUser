package com.development.github.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.github.data.Resource
import com.development.github.data.db.UserEntity
import com.development.github.data.model.ReposItem
import com.development.github.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val repository: GithubRepository): ViewModel() {

    private val _userDetail = MutableLiveData<Resource<UserEntity>>()
    val userDetail: LiveData<Resource<UserEntity>> get() = _userDetail
    fun getUserDetails(username: String){
        viewModelScope.launch {
            val result = repository.getDetailUser(username)
            _userDetail.value = result
        }
    }

    private val _userRepos = MutableLiveData<Resource<List<ReposItem>>>()
    val userRepos: LiveData<Resource<List<ReposItem>>> get() = _userRepos
    fun getRepos(username: String){
        viewModelScope.launch {
            val result = repository.getRepos(username)
            _userRepos.value = result
        }
    }
}