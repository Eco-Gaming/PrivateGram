package me.ecogaming.privategram.network

import me.ecogaming.privategram.entity.Comment
import me.ecogaming.privategram.entity.DetailedPost
import me.ecogaming.privategram.entity.Post
import me.ecogaming.privategram.entity.Profile
import me.ecogaming.privategram.entity.TaggedPostsResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * for documentation of the Proxigram REST API see https://codeberg.org/ThePenguinDev/Proxigram/src/branch/main/README.md#features
 */

@JvmSuppressWildcards
interface ProxigramApi {

    @GET("/api/{username}")
    suspend fun getProfile(
        @Path("username") username: String
    ): Profile

    @GET("/api/{username}/posts")
    suspend fun getProfileFeed(
        @Path("username") username: String,
        @Query("cursor") cursor: String? = null
    ): List<Post>

    @GET("/api/p/{shortcode}")
    suspend fun getPost(
        @Path("shortcode") shortcode: String
    ): DetailedPost

    @GET("/api/p/{shortcode}/comments")
    suspend fun getPostComments(
        @Path("shortcode") shortcode: String
    ): List<Comment>

    @GET("/api/tag/{tag}")
    suspend fun getTaggedPosts(
        @Path("tag") tag: String
    ): TaggedPostsResults
}