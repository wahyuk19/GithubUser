package com.development.github.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.development.github.data.db.UserEntity
import com.development.github.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: GithubRepository): ViewModel() {

    fun searchUsers(username: String): MutableStateFlow<PagingData<UserEntity>>{
        loadUsers(username)
        return _userState
    }

    fun getUsers(): MutableStateFlow<PagingData<UserEntity>>{
        loadAllUsers()
        return _userState
    }

    private val _userState: MutableStateFlow<PagingData<UserEntity>> = MutableStateFlow(value = PagingData.empty())

    private fun loadUsers(username: String){
        viewModelScope.launch {
            getUsers(username)
        }
    }

    private suspend fun getUsers(username: String){
        repository.searchUser(username).distinctUntilChanged().cachedIn(viewModelScope).collect{
            _userState.value = it
        }
    }

    private fun loadAllUsers(){
        viewModelScope.launch {
            getAllUsers()
        }
    }

    private suspend fun getAllUsers(){
        repository.getUsers().distinctUntilChanged().cachedIn(viewModelScope).collect{
            _userState.value = it
        }
    }
}