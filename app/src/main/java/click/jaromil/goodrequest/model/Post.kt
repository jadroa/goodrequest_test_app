package click.jaromil.goodrequest.model

import android.databinding.BaseObservable


data class Post(
    val body: String?,
    val id: Int?,
    val title: String?,
    val userId: Int?
): BaseObservable()