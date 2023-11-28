package com.development.github.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.development.github.data.api.GithubApi
import com.development.github.data.db.UserEntity

class UsersPagingSource(private val token: String,private val api: GithubApi): PagingSource<Int, UserEntity>() {
    override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {
        return try{
            val position = params.key ?: INITIAL_ID
            val responseData = api.getUsers(token,position)

            LoadResult.Page(
                data = responseData,
                prevKey = if(position == INITIAL_ID) null else position -1,
                nextKey = if(responseData.isEmpty()) null else responseData[responseData.size-1].id
            )
        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    private companion object{
        const val INITIAL_ID = 1
    }
}