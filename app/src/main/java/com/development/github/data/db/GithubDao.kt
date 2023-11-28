package com.development.github.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: List<UserEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: UserEntity)

    @Query("DELETE FROM github_users")
    suspend fun delete()

    @Query("SELECT * FROM github_users ORDER BY id ASC")
    fun getAllUsersAsc(): PagingSource<Int,UserEntity>
}