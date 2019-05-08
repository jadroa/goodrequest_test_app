package click.jaromil.goodrequest.api

import click.jaromil.goodrequest.model.Post
import click.jaromil.goodrequest.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GRApi {
    @GET("users")
    fun getAllUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<List<User>>
    
    @GET("users/{userId}")
    fun getUserByIdRx(
        @Path("userId") userId: Int
    ): Single<User>

    @GET("posts")
    fun getUserPostsRx(
        @Query("userId") userId: Int
    ): Single<List<Post>>
}