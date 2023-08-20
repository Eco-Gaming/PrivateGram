package me.ecogaming.privategram.entity

data class Profile(
    val username: String,
    val profilePicture: String,
    val isPrivate: Boolean,
    val fullname: String,
    val biography: String,
    val tags: List<String>,
    val users: List<String>,
    val mediaCount: Int,
    val followers: Int,
    val following: Int
)