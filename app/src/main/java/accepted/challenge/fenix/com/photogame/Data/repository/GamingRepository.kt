package accepted.challenge.fenix.com.photogame.Data.repository

import accepted.challenge.fenix.com.photogame.Data.ApiService
import accepted.challenge.fenix.com.photogame.Data.model.ApiDataResponse
import accepted.challenge.fenix.com.photogame.Data.model.ApiMessageResponse
import accepted.challenge.fenix.com.photogame.Data.model.PhotoResponse
import accepted.challenge.fenix.com.photogame.Domain.Constants
import accepted.challenge.fenix.com.photogame.Domain.GameUpdateType
import accepted.challenge.fenix.com.photogame.Domain.PrefManager
import accepted.challenge.fenix.com.photogame.app.Models.GameUploadDetails
import io.reactivex.Single
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GamingRepository(private val apiService: ApiService,
                       private val realm: Realm,
                       private val prefManager: PrefManager) {

    private val token: String?
        get() = prefManager.user?.token

    /**
     * Upload picture data
     *
     * @param gameUploadDetails
     *
     * @return [Single]
     */
    fun upload(gameUploadDetails: GameUploadDetails): Single<Boolean> {
        return Single.create { emitter ->
            apiService.uploadPic(
                    gameUploadDetails.pic,
                    gameUploadDetails.caption,
                    gameUploadDetails.description ?: "",
                    gameUploadDetails.category ?: "",
                    gameUploadDetails.location ?: "",
                    prefManager.user?.token ?: ""
            ).enqueue(object : Callback<ApiMessageResponse> {
                override fun onFailure(call: Call<ApiMessageResponse>, t: Throwable) {
                    emitter.onError(t)
                }

                override fun onResponse(call: Call<ApiMessageResponse>, response: Response<ApiMessageResponse>) {
                    if (response.isSuccessful && response.body()?.message == Constants.SUCCESS_UPLOAD)
                        emitter.onSuccess(true)
                    else emitter.onSuccess(false)
                }

            })
        }
    }

    /**
     * Fetch photos
     *
     * @return [Single]
     */
    fun fetch(): Single<Array<GameUploadDetails>> {
        return Single.create { emitter ->
            token?.let { _token ->
                apiService.fetchPhotos(_token)
                        .enqueue(object : Callback<ApiDataResponse<PhotoResponse>> {
                            override fun onFailure(call: Call<ApiDataResponse<PhotoResponse>>, t: Throwable) {
                                emitter.onError(t)
                            }

                            override fun onResponse(call: Call<ApiDataResponse<PhotoResponse>>,
                                                    response: Response<ApiDataResponse<PhotoResponse>>) {
                                if (response.isSuccessful) {
                                    val gamePhotos = response.body()?.response?.photos
                                    emitter.onSuccess(gamePhotos!!)
                                } else emitter.onError(Throwable())
                            }
                        })
            }

        }
    }

    /**
     * Updates photos likes
     *
     * @param photoId [String]
     *
     * @return [Single]
     */
    fun likedPic(photoId: Int): Single<Boolean> =
            updateGame(makeCallback(token!!, photoId, GameUpdateType.LIKE))
                    .flatMap { viewedPic(photoId) }

    /**
     * Updates photos dislikes
     *
     * @param photoId [String]
     *
     * @return [Single]
     */
    fun dislikedPic(photoId: Int): Single<Boolean> =
            updateGame(makeCallback(token!!, photoId, GameUpdateType.DISLIKE))
                    .flatMap { viewedPic(photoId) }

    /**
     * Updates photos views
     *
     * @param photoId [String]
     *
     * @return [Single]
     */
    private fun viewedPic(photoId: Int): Single<Boolean> =
            updateGame(makeCallback(token!!, photoId, GameUpdateType.VIEW))

    /**
     * Builds call using [token], [photoId] and [gameUpdateType]
     *
     * @param token
     * @param photoId
     * @param gameUpdateType
     *
     * @return [Call]
     */
    private fun makeCallback(token: String, photoId: Int, gameUpdateType: GameUpdateType): Call<ApiMessageResponse> {
        return when (gameUpdateType) {
            GameUpdateType.DISLIKE -> apiService.dislikePic(photoId, token)
            GameUpdateType.LIKE -> apiService.dislikePic(photoId, token)
            GameUpdateType.VIEW -> apiService.dislikePic(photoId, token)
        }
    }

    /**
     * Using the built call from [makeCallback], makes api call and returns boolean status
     *
     * @return [Single]
     */
    private fun updateGame(apiCall: Call<ApiMessageResponse>): Single<Boolean> {
        return Single.create { emitter ->
            apiCall.enqueue(object : Callback<ApiMessageResponse> {
                override fun onFailure(call: Call<ApiMessageResponse>, t: Throwable) {
                    emitter.onError(t)
                }

                override fun onResponse(call: Call<ApiMessageResponse>, response: Response<ApiMessageResponse>) {
                    if (response.isSuccessful && response.body()?.message == Constants.SUCCESS_MESSAGE)
                        emitter.onSuccess(true)
                    else emitter.onError(Throwable())
                }

            })
        }
    }

}