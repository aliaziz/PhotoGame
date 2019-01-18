package accepted.challenge.fenix.com.photogame.Domain

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.Models.GameUploadDetails
import accepted.challenge.fenix.com.photogame.app.app
import android.app.IntentService
import android.content.Intent
import io.reactivex.disposables.CompositeDisposable

const val MODEL_DATA_KEY = "gamingViewModel"

class UploadService : IntentService("UploadService") {
    private lateinit var gameRepository: GamingRepository

    override fun onHandleIntent(intent: Intent?) {
        gameRepository = GamingRepository(app.api(this),
                app.getRealm(),
                app.providePrefManager(this))

        val data: GameUploadDetails = intent?.extras?.getSerializable(MODEL_DATA_KEY) as GameUploadDetails
        val disposable = gameRepository.upload(data).subscribe({
            toast(getString(R.string.uploaded))
        }, {
            toast(getString(R.string.error_uploading))
        })

    }
}
