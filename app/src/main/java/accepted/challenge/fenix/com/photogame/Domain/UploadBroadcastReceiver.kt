package accepted.challenge.fenix.com.photogame.Domain

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.app.app
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo


class UploadBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        (intent?.extras?.get("networkInfo") as NetworkInfo).let { it ->
            if (it.state.name == "CONNECTED") {
                val gameRepo = GamingRepository(
                        app.api(context!!),
                        app.getRealm(),
                        app.providePrefManager(context)
                )

                if (gameRepo.hasPendingUpload)
                    gameRepo.getSavedUpload().forEach { remoteData ->
                        gameRepo.upload(remoteData)
                    }
            }

        }
    }
}