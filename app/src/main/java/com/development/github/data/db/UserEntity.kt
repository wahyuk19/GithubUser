package com.development.github.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github_users", indices = [Index(value = ["id"], unique = true)])
data class UserEntity(
    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    val avatarUrl: String?,

    @ColumnInfo(name = "bio")
    val bio: String?,

    @ColumnInfo(name = "blog")
    val blog: String?,

    @ColumnInfo(name = "company")
    val company: String?,

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    val createdAt: String?,

    @ColumnInfo(name = "email")
    val email: String?,

    @ColumnInfo(name = "events_url")
    @SerializedName("events_url")
    val eventsUrl: String?,

    @ColumnInfo(name = "followers")
    val followers: Int?,

    @ColumnInfo(name = "followers_url")
    @SerializedName("followers_url")
    val followersUrl: String?,

    @ColumnInfo(name = "following")
    val following: Int?,

    @ColumnInfo(name = "following_url")
    @SerializedName("following_url")
    val followingUrl: String?,

    @ColumnInfo(name = "gists_url")
    @SerializedName("gists_url")
    val gistsUrl: String?,

    @ColumnInfo(name = "gravatar_id")
    @SerializedName("gravatar_id")
    val gravatarId: String?,

    @ColumnInfo(name = "hireable")
    val hireable: Boolean?,

    @ColumnInfo(name = "html_url")
    @SerializedName("html_url")
    val htmlUrl: String?,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "login")
    val login: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "node_id")
    @SerializedName("node_id")
    val nodeId: String?,

    @ColumnInfo(name = "organizations_url")
    @SerializedName("organizations_url")
    val organizationsUrl: String?,

    @ColumnInfo(name = "public_gists")
    @SerializedName("public_gists")
    val publicGists: Int,

    @ColumnInfo(name = "public_repos")
    @SerializedName("public_repos")
    val publicRepos: Int,

    @ColumnInfo(name = "received_events_url")
    @SerializedName("received_events_url")
    val receivedEventsUrl: String?,

    @ColumnInfo(name = "repos_url")
    @SerializedName("repos_url")
    val reposUrl: String?,

    @ColumnInfo(name = "site_admin")
    @SerializedName("site_admin")
    val siteAdmin: String?,

    @ColumnInfo(name = "starred_url")
    @SerializedName("starred_url")
    val starredUrl: String?,

    @ColumnInfo(name = "subscriptions_url")
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String?,

    @ColumnInfo(name = "twitter_username")
    @SerializedName("twitter_username")
    val twitterUsername: String?,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    val updatedAt: String?,

    @ColumnInfo(name = "url")
    val url: String?
)