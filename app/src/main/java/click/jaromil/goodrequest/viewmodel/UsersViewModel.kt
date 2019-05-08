package click.jaromil.goodrequest.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import click.jaromil.goodrequest.model.Post
import click.jaromil.goodrequest.model.User
import click.jaromil.goodrequest.repository.Repository
import click.jaromil.goodrequest.api.ErrorHandler

class UsersViewModel(private val apiRepository: Repository) : ViewModel() {
    val error = MutableLiveData<String>()
    val currentUser =  MutableLiveData<User>()
    val userPage = MutableLiveData<Int>()
    val userItems = ObservableArrayList<User>()
    val postItems = ObservableArrayList<Post>()
    val isLoading = ObservableField<Boolean>()
    
    init {
        userPage.value = 0
    }
    
    companion object {
        const val PER_PAGE = 25
    }

    override fun onCleared() {
        apiRepository.dispose()
    }
    
    /**
     * get all users
     *
     * @param page current page to be loaded (this parameter is not used as paging is not working)
     */
    fun getAllUsers(page: Int) {
        isLoading.set(true)
        apiRepository.getUsers(page, PER_PAGE, { users ->
            userItems.clear()
            userItems.addAll(users)
            userPage.value = userPage.value?.inc()
            isLoading.set(false)
        }, { throwable ->
            error.value = ErrorHandler.processError(throwable)
            isLoading.set(false)
        })
    }
    
    
    /**
     * get user details with user posts
     *
     * @param userId id of user
     */
    fun getUserDetails(userId: Int) {
        isLoading.set(true)
        apiRepository.getUserDetails(userId, { userDetails ->
            postItems.clear()
            postItems.addAll(userDetails.posts)
            isLoading.set(false)
        }, { throwable ->
            error.value = ErrorHandler.processError(throwable)
            isLoading.set(false)
        })
    }
}