package click.jaromil.goodrequest

import android.app.Application
import click.jaromil.goodrequest.di_module.AppModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class GrUsersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    
        startKoin(this, listOf(AppModule.appModule))
    
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    
        Timber.plant(Timber.DebugTree())
    }
}