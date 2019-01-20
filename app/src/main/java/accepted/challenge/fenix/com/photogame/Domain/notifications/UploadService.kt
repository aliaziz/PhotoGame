package accepted.challenge.fenix.com.photogame.Domain.notifications

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.Domain.Helpers
import accepted.challenge.fenix.com.photogame.Domain.toast
import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.Models.RemoteGameUploadDetails
import android.app.IntentService
import android.content.Intent
import dagger.android.AndroidInjection
import javax.inject.Inject

const val MODEL_DATA_KEY = "gamingViewModel"

class UploadService : IntentService("UploadService") {

    @Inject
    lateinit var gameRepository: GamingRepository

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        val data: RemoteGameUploadDetails = intent?.extras?.getSerializable(MODEL_DATA_KEY) as RemoteGameUploadDetails

        if (Helpers.isOnline(this)) {

            val disposable = gameRepository.upload(data).subscribe({
                toast(getString(R.string.uploaded))
            }, {
                toast(getString(R.string.error_uploading))
            })
        } else gameRepository.saveUpload(data)
    }
}
