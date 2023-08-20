package me.ecogaming.privategram.entity

data class Profile(
    val username: String,
    val profilePicture: String,
    val isPrivate: Boolean,
    val fullname: String,
    val biography: String,
    val tags: Array<String>,
    val users: Array<String>,
    val mediaCount: Int,
    val followers: Int,
    val following: Int
)