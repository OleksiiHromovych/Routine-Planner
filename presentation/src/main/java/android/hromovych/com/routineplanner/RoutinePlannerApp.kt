package android.hromovych.com.routineplanner

import android.app.Application
import android.hromovych.com.routineplanner.di.PresentationLayerDI
import com.hromovych.data.di.DataLayerDI
import com.hromovych.domain.di.DomainLayerDI
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