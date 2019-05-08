package click.jaromil.goodrequest.repository

import click.jaromil.goodrequest.model.Post
import click.jaromil.goodrequest.model.User
import click.jaromil.goodrequest.model.UserDetails

interface Repository {
    fun getUsers(page: Int, perPage: Int, responseListener: (List<User>) -> Unit, errorListener: (Throwable) -> Unit)
    fun getUserDetails(userId: Int, responseListener: (UserDetails) -> Unit, errorListener: (Throwable) -> Unit)
    fun dispose()
}