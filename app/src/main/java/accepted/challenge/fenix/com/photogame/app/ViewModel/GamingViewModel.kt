package accepted.challenge.fenix.com.photogame.app.ViewModel

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.app.Models.GameUploadDetails
import android.arch.lifecycle.ViewModel
import io.reactivex.Single

class GamingViewModel(private val gamingRepository: GamingRepository): ViewModel() {

    fun uploadPic(gamingModel: GameUploadDetails? = null): Single<Boolean> = Single.create {  }

}