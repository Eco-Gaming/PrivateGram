package me.ecogaming.privategram.entity

import com.google.gson.annotations.SerializedName

data class DetailedPost(
    val id: String,
    val shortcode: String,
    val author: Author,
    val description: String,
    val tags: List<String>,
    val users: List<String>,
    @SerializedName("created_at") val createdAt: CreatedAt,
    val commentsCount: Int,
    val likes: Int,
    val isVideo: Boolean,
    val isSideCard: Boolean,
    val thumb: String,
    val sidecard: List<SideCard>,
    val video: String
)