package accepted.challenge.fenix.com.photogame.Domain.di

import accepted.challenge.fenix.com.photogame.app.View.LoginRegActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun loginActivityInjector(): LoginRegActivity
}