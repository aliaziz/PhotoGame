package accepted.challenge.fenix.com.photogame.app

import accepted.challenge.fenix.com.photogame.Domain.di.DaggerAppComponent
import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import android.support.v4.app.Fragment
import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class PhotoGame: Application(), HasActivityInjector,
        HasBroadcastReceiverInjector, HasServiceInjector, HasSupportFragmentInjector
{

    @Inject
    internal lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    internal lateinit var broadcastReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>

    @Inject
    internal lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    @Inject
    internal lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun serviceInjector(): AndroidInjector<Service>  = serviceInjector

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver>
            =  broadcastReceiverInjector

    override fun supportFragmentInjector(): AndroidInjector<android.support.v4.app.Fragment>  = supportFragmentInjector

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