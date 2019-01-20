package accepted.challenge.fenix.com.photogame.Domain.notifications

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import dagger.android.AndroidInjection
import javax.inject.Inject


class UploadBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var gameRepo: GamingRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this, context)

        (intent?.extras?.get("networkInfo") as NetworkInfo).let { it ->
            if (it.state.name == "CONNECTED") {
                if (gameRepo.hasPendingUpload)
                    gameRepo.getSavedUpload().forEach { remoteData ->
                        gameRepo.upload(remoteData).subscribe()
                    }
            }

        }
    }
}