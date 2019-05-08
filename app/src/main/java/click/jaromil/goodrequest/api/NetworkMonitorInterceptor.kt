package click.jaromil.goodrequest.api

import okhttp3.Interceptor
import okhttp3.Response

class NetworkMonitorInterceptor(private val networkMonitor: NetworkMonitor) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        networkMonitor?.let {
            if (it.isConnected()) {
                if (chain != null)
                    return chain.proceed(chain.request())
            } else {
                throw NotConnectedException(networkMonitor.getMessage())
            }
        }
        return chain.proceed(chain.request())
    }
}