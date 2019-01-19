package accepted.challenge.fenix.com.photogame.app.ViewModel

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.Domain.ErrorMessages
import accepted.challenge.fenix.com.photogame.Domain.GameUpdateType
import accepted.challenge.fenix.com.photogame.app.Models.GameUploadDetails
import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class GamingViewModel(private val gamingRepository: GamingRepository): ViewModel(){

    private var gameUploadDetails: ArrayList<GameUploadDetails>? = ArrayList()

    var nextPic = PublishSubject.create<String>()
    var messageSubscription = PublishSubject.create<String>()

    /**
     * Initialise view mode calls
     */
    fun init() { loadGamePics() }
    /**
     * Make repository call to upload player info
     *
     * @param gamingModel
     *
     * @return [Single]
     */
    fun uploadPic(gamingModel: GameUploadDetails): Single<Boolean>
            = gamingRepository.upload(gamingModel)

    fun likePic() { updateGamePhoto(GameUpdateType.LIKE) }

    fun dislikePic() { updateGamePhoto(GameUpdateType.DISLIKE) }

    /**
     * General function called to update game data
     *
     * @param gameUpdateType [GameUpdateType]
     */
    private fun updateGamePhoto(gameUpdateType: GameUpdateType) {
        gameUploadDetails?.let { gameDetails ->

            val updateMethod = when(gameUpdateType) {
                GameUpdateType.LIKE -> gamingRepository.likedPic(gameDetails[0].photoId)
                GameUpdateType.DISLIKE -> gamingRepository.dislikedPic(gameDetails[0].photoId)
                else -> Single.just(false)
            }

            updateMethod.subscribe({
                if (it) {
                    gameUploadDetails?.removeAt(0)
                    gameDetails.removeAt(0)
                    if (gameDetails.isNotEmpty())
                        loadNextPic(gameDetails[0].pic)
                    else messageSubscription.onNext(ErrorMessages.NO_MORE_DATA.name)
                } else messageSubscription.onNext(ErrorMessages.LIKE_ERROR.name)
            },{ messageSubscription.onNext(ErrorMessages.LIKE_ERROR.name)})
        }
    }

    @SuppressLint("CheckResult")
    private fun loadGamePics() {
        gamingRepository.fetch().subscribe({ pics ->
            if (pics.isNotEmpty()) {
                gameUploadDetails?.addAll(pics)
                loadNextPic(pics[0].pic)

            } else messageSubscription.onNext(ErrorMessages.NO_MORE_DATA.name)

        }, { messageSubscription.onNext(ErrorMessages.LOAD_ERROR.name)})
    }

    private fun loadNextPic(url: String) {
        nextPic.onNext(url)
    }
}