package spaceLaunch.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import spaceLaunch.di.*

class SpaceLaunch: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SpaceLaunch)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    mapperModule
                )
            )
        }
    }
}