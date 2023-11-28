package com.development.github.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class,RemoteKeys::class], version = 2, exportSchema = false)
abstract class GithubRoomDatabase: RoomDatabase() {
    abstract fun githubDao(): GithubDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}