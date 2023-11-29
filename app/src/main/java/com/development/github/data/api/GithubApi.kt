package com.development.github.data.api

import com.development.github.data.db.UserEntity
import com.development.github.data.model.ReposItem
import com.development.github.data.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun searchUser(
        @Header("Authorization") token: String,
        @Query("q") username: String,
        @Query("page") page: Int
    ): SearchResult

    @GET("users")
    suspend fun getUsers(
        @Header("Authorization") token: String,
        @Query("since") lastId: Int
    ): List<UserEntity>

    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): List<ReposItem>

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): UserEntity
}