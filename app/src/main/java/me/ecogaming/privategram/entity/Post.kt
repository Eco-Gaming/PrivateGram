package me.ecogaming.privategram.entity

data class Post(
    val id: String,
    val shortcode: String,
    val description: String,
    val thumb: String,
    val isVideo: Boolean
)