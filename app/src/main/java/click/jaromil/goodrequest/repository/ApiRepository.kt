package click.jaromil.goodrequest.repository

import click.jaromil.goodrequest.api.ApiClient
import click.jaromil.goodrequest.model.Post
import click.jaromil.goodrequest.model.User
import click.jaromil.goodrequest.model.UserDetails
import click.jaromil.goodrequest.util.zipWithTimer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ApiRepository(private val apiClient: ApiClient) : Repository {
    private val compositeDisposable = CompositeDisposable()
    
    override fun dispose() {
        compositeDisposable.clear()
    }
    
    override fun getUsers(
        page: Int,
        perPage: Int,
        responseListener: (List<User>) -> Unit,
        errorListener: (Throwable) -> Unit
    ) {
        val disposable = zipWithTimer(apiClient.grApi.getAllUsers(page, perPage))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { users ->
                    users?.let {
                        responseListener(it)
                    } ?: run {
                        errorListener(Throwable("Users null!!!"))
                    }
                },
                onError = {
                    errorListener(it)
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun getUserDetails(
        userId: Int,
        responseListener: (UserDetails) -> Unit,
        errorListener: (Throwable) -> Unit
    ) {
        val userSingle = apiClient.grApi.getUserByIdRx(userId)

        val postsSingle = apiClient.grApi.getUserPostsRx(userId)

        val userDetailsSingle: Single<UserDetails> = Single.zip(userSingle, postsSingle,
            BiFunction<User, List<Post>, UserDetails> { user, posts ->
                UserDetails(user, posts)
            })

        val disposable = zipWithTimer(userDetailsSingle)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { user ->
                    user?.let {
                        responseListener(it)
                    } ?: run {
                        errorListener(Throwable("UserDetails null!!!"))
                    }
                },
                onError = {
                    errorListener(it)
                }
            )
        compositeDisposable.add(disposable)
    }
}