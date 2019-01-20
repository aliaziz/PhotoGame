package accepted.challenge.fenix.com.photogame.app

import accepted.challenge.fenix.com.photogame.Domain.di.DaggerAppComponent
import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.app.Service
import android.content.BroadcastReceiver
import dagger.android.*
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class PhotoGame: Application(), HasActivityInjector,
        HasBroadcastReceiverInjector, HasServiceInjector, HasFragmentInjector
{

    @Inject
    internal lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    internal lateinit var broadcastReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>

    @Inject
    internal lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    @Inject
    internal lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun serviceInjector(): AndroidInjector<Service>  = serviceInjector

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver>
            =  broadcastReceiverInjector

    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        Realm.init(this)
        val config = RealmConfiguration.Builder().name("photoGame.realm").build()
        Realm.setDefaultConfiguration(config)
    }

}