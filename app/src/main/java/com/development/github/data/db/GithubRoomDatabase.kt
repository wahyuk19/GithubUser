package com.development.github.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = true)
abstract class GithubRoomDatabase: RoomDatabase() {
    abstract fun githubDao(): GithubDao
}