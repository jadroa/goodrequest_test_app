package click.jaromil.goodrequest.api

import android.content.Context
import android.net.ConnectivityManager
import click.jaromil.goodrequest.R

class NetworkMonitor(val appContext: Context) {
    fun isConnected(): Boolean {
        val cm: ConnectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val an = cm.activeNetworkInfo
        return an != null
    }

    fun getMessage() = appContext.getString(R.string.check_internet_connection)
}