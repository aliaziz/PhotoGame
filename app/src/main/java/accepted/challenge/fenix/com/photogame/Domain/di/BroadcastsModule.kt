package accepted.challenge.fenix.com.photogame.Domain.di

import accepted.challenge.fenix.com.photogame.Domain.notifications.UploadBroadcastReceiver
import accepted.challenge.fenix.com.photogame.Domain.notifications.UploadService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BroadcastsModule {

    @ContributesAndroidInjector
    abstract fun loadBroadCastReceiver(): UploadBroadcastReceiver

    @ContributesAndroidInjector
    abstract fun loadUploadService(): UploadService
}