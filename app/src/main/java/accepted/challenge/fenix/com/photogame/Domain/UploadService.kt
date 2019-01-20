package accepted.challenge.fenix.com.photogame.Domain

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.Models.RemoteGameUploadDetails
import accepted.challenge.fenix.com.photogame.app.app
import android.app.IntentService
import android.content.Intent

const val MODEL_DATA_KEY = "gamingViewModel"

class UploadService : IntentService("UploadService") {
    private lateinit var gameRepository: GamingRepository

    override fun onHandleIntent(intent: Intent?) {
        val data: RemoteGameUploadDetails = intent?.extras?.getSerializable(MODEL_DATA_KEY) as RemoteGameUploadDetails

        gameRepository = GamingRepository(app.api(this),
                app.getRealm(),
                app.providePrefManager(this))

        if (isOnline(this)) {

            val disposable = gameRepository.upload(data).subscribe({
                toast(getString(R.string.uploaded))
            }, {
                toast(getString(R.string.error_uploading))
            })
        } else gameRepository.saveUpload(data)
    }
}
