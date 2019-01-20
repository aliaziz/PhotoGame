package accepted.challenge.fenix.com.photogame.app.ViewModel

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.Domain.ErrorMessages
import accepted.challenge.fenix.com.photogame.Domain.GameUpdateType
import accepted.challenge.fenix.com.photogame.app.Models.RemoteGameUploadDetails
import accepted.challenge.fenix.com.photogame.app.Models.LeaderShipModel
import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class GamingViewModel(private val gamingRepository: GamingRepository) : ViewModel() {

    private var remoteGameUploadDetails: ArrayList<RemoteGameUploadDetails>? = ArrayList()

    var nextPic = PublishSubject.create<String>()
    var showLoader = PublishSubject.create<Boolean>()
    var messageSubscription = PublishSubject.create<String>()

    /**
     * Initialise view mode calls
     */
    fun init() {
        loadGamePics()
    }

    fun likePic() {
        showLoader.onNext(true)
        updateGamePhoto(GameUpdateType.LIKE)
    }

    fun dislikePic() {
        showLoader.onNext(true)
        updateGamePhoto(GameUpdateType.DISLIKE)
    }

    fun getScores(): Single<Array<LeaderShipModel>> = gamingRepository.getLeadersResults()

    /**
     * General function called to update game data
     *
     * @param gameUpdateType [GameUpdateType]
     */
    private fun updateGamePhoto(gameUpdateType: GameUpdateType) {
        remoteGameUploadDetails?.let { gameDetails ->

            if (gameDetails.size > 0) {
                val updateMethod = when (gameUpdateType) {
                    GameUpdateType.LIKE -> gamingRepository.likedPic(gameDetails[0].photoId)
                    GameUpdateType.DISLIKE -> gamingRepository.dislikedPic(gameDetails[0].photoId)
                    else -> Single.just(false)
                }

                updateMethod
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            moveToNextDetail(gameDetails)

                            if (!it) messageSubscription.onNext(ErrorMessages.LIKE_ERROR.name)

                        }, {
                            moveToNextDetail(gameDetails)
                            messageSubscription.onNext(ErrorMessages.LIKE_ERROR.name)
                        }
                        )
            }
        }
    }

    private fun moveToNextDetail(remoteGameDetails: ArrayList<RemoteGameUploadDetails>) {
        showLoader.onNext(false)
        if (remoteGameDetails.size >= 1)
            remoteGameUploadDetails?.removeAt(0)

        if (remoteGameDetails.isNotEmpty())
            loadNextPic(remoteGameDetails[0].pic)
        else messageSubscription.onNext(ErrorMessages.NO_MORE_DATA.name)
    }

    @SuppressLint("CheckResult")
    private fun loadGamePics() {
        gamingRepository.fetch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ pics ->
                    if (pics.isNotEmpty()) {
                        remoteGameUploadDetails?.addAll(pics)
                        loadNextPic(pics[0].pic)

                    } else messageSubscription.onNext(ErrorMessages.NO_MORE_DATA.name)

                }, { messageSubscription.onNext(ErrorMessages.LOAD_ERROR.name) })
    }

    private fun loadNextPic(url: String) {
        nextPic.onNext(url)
    }
}