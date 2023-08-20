package me.ecogaming.privategram.entity

data class PostList(
    val posts: List<Post>,
    val cursor: String
)