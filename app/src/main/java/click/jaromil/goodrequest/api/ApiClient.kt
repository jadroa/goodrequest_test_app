package click.jaromil.goodrequest.api

import android.content.Context
import click.jaromil.goodrequest.BuildConfig
import click.jaromil.goodrequest.BuildConfig.BASE_URL
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(val context: Context) {
    val grApi: GRApi by lazy {
        getRestClient()
    }
    
    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(
                NetworkMonitorInterceptor(
                    NetworkMonitor(
                        context
                    )
                )
            )
            .addInterceptor(HttpLoggingInterceptor()
                .setLevel(
                    if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE))
            .build()
    }
    
    private fun getMoshiClient(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private fun getRestClient(): GRApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(getMoshiClient()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(GRApi::class.java)
    }
}