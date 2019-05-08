package click.jaromil.goodrequest.model
import android.databinding.BaseObservable


data class User(
    val address: Address?,
    val company: Company?,
    val email: String?,
    val id: Int?,
    val name: String?,
    val phone: String?,
    val username: String?,
    val website: String?
): BaseObservable()

data class Address(
    val city: String?,
    val geo: Geo?,
    val street: String?,
    val suite: String?,
    val zipcode: String?
): BaseObservable()

data class Geo(
    val lat: String?,
    val lng: String?
): BaseObservable()

data class Company(
    val bs: String?,
    val catchPhrase: String?,
    val name: String?
): BaseObservable()