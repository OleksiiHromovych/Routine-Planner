package android.hromovych.com.routineplanner.presentation

import android.app.Application
import android.hromovych.com.routineplanner.data.di.DataLayerDI
import android.hromovych.com.routineplanner.domain.di.DomainLayerDI
import android.hromovych.com.routineplanner.presentation.di.PresentationLayerDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RoutinePlannerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RoutinePlannerApp)
            modules(
                DataLayerDI.dataSourceModule,
                DataLayerDI.mappersModule,
                DataLayerDI.repositoryModule,

                DomainLayerDI.useCaseModule,

                PresentationLayerDI.viewModelModule,
                PresentationLayerDI.tasks
            )
        }
    }

}