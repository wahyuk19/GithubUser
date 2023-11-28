package com.development.github.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.development.github.BuildConfig
import com.development.github.data.Resource
import com.development.github.data.api.GithubApi
import com.development.github.data.db.GithubDao
import com.development.github.data.db.GithubRoomDatabase
import com.development.github.data.db.UserEntity
import com.development.github.data.db.UserRemoteMediator
import com.development.github.data.model.ReposItem
import com.development.github.data.model.SearchResult
import com.development.github.data.paging.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TOKEN_PART = "UVs92gV3bIVzni446jcYk"
@OptIn(ExperimentalPagingApi::class)
class GithubRepository @Inject constructor(
    private val db: GithubRoomDatabase,
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

    fun getUsers(): Flow<PagingData<UserEntity>>{
        return Pager(
            config = PagingConfig(
                pageSize = 30
            ),
            remoteMediator = UserRemoteMediator(BuildConfig.API_TOKEN+TOKEN_PART,db,api),
            pagingSourceFactory = {
                db.githubDao().getAllUsersAsc()
            }
        ).flow
    }

    suspend fun getRepos(username: String): Resource<List<ReposItem>>{
        val response = try{
            Resource.Loading(data = true)
            api.getRepos(BuildConfig.API_TOKEN+TOKEN_PART,username)
        }catch (e: Exception){
            return Resource.Error(message = "An error occured ${e.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
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