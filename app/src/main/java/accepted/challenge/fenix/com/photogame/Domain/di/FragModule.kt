package accepted.challenge.fenix.com.photogame.Domain.di

import accepted.challenge.fenix.com.photogame.app.View.HomeFrags.LeadershipFrag
import accepted.challenge.fenix.com.photogame.app.View.HomeFrags.PlayGameFrag
import accepted.challenge.fenix.com.photogame.app.View.HomeFrags.UploadPicFrag
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragModule {

    @ContributesAndroidInjector
    abstract fun loadUploadPicFrag(): UploadPicFrag

    @ContributesAndroidInjector
    abstract fun loadPlayGameFrag(): PlayGameFrag

    @ContributesAndroidInjector
    abstract fun loadLeadershipFrag(): LeadershipFrag
}