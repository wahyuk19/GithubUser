package com.development.github.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.development.github.BuildConfig
import com.development.github.data.Resource
import com.development.github.data.api.GithubApi
import com.development.github.data.db.GithubDao
import com.development.github.data.db.UserEntity
import com.development.github.data.model.SearchResult
import com.development.github.data.paging.SearchPagingSource
import com.development.github.data.paging.UsersPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TOKEN_PART = "UVs92gV3bIVzni446jcYk"
class GithubRepository @Inject constructor(
    private val githubDao: GithubDao,
    private val api: GithubApi
) {

    fun searchUser(username: String): Flow<PagingData<UserEntity>>{
        return Pager(
            config = PagingConfig(
                pageSize = 30
            ),
            pagingSourceFactory = {
                SearchPagingSource(BuildConfig.API_TOKEN+TOKEN_PART,username, api)
            }
        ).flow
    }

    suspend fun getUser(): Flow<PagingData<UserEntity>>{
        return Pager(
            config = PagingConfig(
                pageSize = 30
            ),
            pagingSourceFactory = {
                UsersPagingSource(BuildConfig.API_TOKEN+TOKEN_PART,api)
            }
        ).flow
    }

    suspend fun getDetailUser(username: String): Resource<UserEntity>{
        val response = try{
            Resource.Loading(data = true)
            api.getUserDetail(BuildConfig.API_TOKEN+TOKEN_PART,username)
        }catch (e: Exception){
            return Resource.Error(message = "An error occured ${e.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}