package com.development.github.data.model

import com.development.github.data.db.UserEntity
import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("incomplete_result")
    val incompleteResults: Boolean,
    val items: List<UserEntity>,
    @SerializedName("total_count")
    val totalCount: Int
)