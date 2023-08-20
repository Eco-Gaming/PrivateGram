package me.ecogaming.privategram.entity

data class TaggedPostsResults(
    val tag: String,
    val posts: List<Post>
)