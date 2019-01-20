package accepted.challenge.fenix.com.photogame.Domain.di

import accepted.challenge.fenix.com.photogame.app.PhotoGame
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@Component(modules = [NetModule::class,
    ActivityModule::class,
    AndroidInjectionModule::class])

interface AppComponent {

    fun inject(application: PhotoGame)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}