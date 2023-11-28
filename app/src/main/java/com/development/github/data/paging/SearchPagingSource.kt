package com.development.github.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.development.github.data.api.GithubApi
import com.development.github.data.db.UserEntity

class SearchPagingSource(private val token: String,private val username: String,private val api: GithubApi): PagingSource<Int, UserEntity>() {

    override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {
        return try{
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = api.searchUser(token,username,position)

            LoadResult.Page(
                data = responseData.items,
                prevKey = if(position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if(responseData.items.isEmpty()) null else position +1
            )
        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }
}