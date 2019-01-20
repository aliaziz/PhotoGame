package accepted.challenge.fenix.com.photogame.Data.repository

import accepted.challenge.fenix.com.photogame.Data.ApiService
import accepted.challenge.fenix.com.photogame.Data.model.*
import accepted.challenge.fenix.com.photogame.Domain.managers.Constants
import accepted.challenge.fenix.com.photogame.Domain.managers.GameUpdateType
import accepted.challenge.fenix.com.photogame.Domain.managers.PrefManager
import accepted.challenge.fenix.com.photogame.app.Models.RemoteGameUploadDetails
import accepted.challenge.fenix.com.photogame.app.Models.LeaderShipModel
import io.reactivex.Single
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

open class GamingRepository @Inject constructor(private val apiService: ApiService,
                       private val realm: Realm,
                       private val prefManager: PrefManager) {

    private val token: String?
        get() = prefManager.user?.token

    var hasPendingUpload: Boolean
        get() = prefManager.hasPendingUpload
        private set(value) {
            prefManager.hasPendingUpload = value
        }

    /**
     * Upload picture data
     *
     * @param remoteGameUploadDetails
     *
     * @return [Single]
     */
    fun upload(remoteGameUploadDetails: RemoteGameUploadDetails): Single<Boolean> {
        return Single.create { emitter ->
            apiService.uploadPic(
                    remoteGameUploadDetails.pic,
                    remoteGameUploadDetails.caption,
                    remoteGameUploadDetails.description ?: "",
                    remoteGameUploadDetails.category ?: "",
                    remoteGameUploadDetails.location ?: "",
                    prefManager.user?.token ?: ""
            ).enqueue(object : Callback<ApiMessageResponse> {
                override fun onFailure(call: Call<ApiMessageResponse>, t: Throwable) {
                    emitter.onError(t)
                }

                override fun onResponse(call: Call<ApiMessageResponse>, response: Response<ApiMessageResponse>) {
                    if (response.isSuccessful && response.body()?.message == Constants.SUCCESS_UPLOAD) {
                        emitter.onSuccess(true)
                        hasPendingUpload = false
                    } else emitter.onSuccess(false)
                }

            })
        }
    }

    /**
     * Fetch photos
     *
     * @return [Single]
     */
    fun fetch(): Single<Array<RemoteGameUploadDetails>> {
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

    /**
     * Updates photos dislikes
     *
     * @param photoId [String]
     *
     * @return [Single]
     */
    fun dislikedPic(photoId: Int): Single<Boolean> =
            updateGame(makeCallback(token!!, photoId, GameUpdateType.DISLIKE))

    /**
     * Fetches leading players scores
     *
     * @return [Single]
     */
    fun getLeadersResults(): Single<Array<LeaderShipModel>> {
        return Single.create { emitter ->
            token?.let { _token ->
                apiService.getLeadershipBoard(_token)
                        .enqueue(object : Callback<ApiDataResponse<LeadershipResponse>> {
                            override fun onFailure(call: Call<ApiDataResponse<LeadershipResponse>>, t: Throwable) {
                                emitter.onError(t)
                            }

                            override fun onResponse(call: Call<ApiDataResponse<LeadershipResponse>>, response: Response<ApiDataResponse<LeadershipResponse>>) {
                                if (response.isSuccessful) {
                                    response.body()?.response?.leadership?.let {
                                        emitter.onSuccess(it)
                                    }
                                } else emitter.onError(Throwable())
                            }

                        })
            }
        }
    }

    /**
     * Saves pending remote upload
     *
     * @param remoteGameUploadDetails
     */
    fun saveUpload(remoteGameUploadDetails: RemoteGameUploadDetails) {
        val localUploadDetails
                = LocalGameUploadDetails(remoteGameUploadDetails.pic,
                remoteGameUploadDetails.caption,
                remoteGameUploadDetails.description,
                remoteGameUploadDetails.category,
                remoteGameUploadDetails.location)
        hasPendingUpload = true
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(localUploadDetails)
        realm.commitTransaction()
    }

    /**
     * Gets pending remote uploads
     *
     * @return [Array]<[RemoteGameUploadDetails]>
     */
    fun getSavedUpload(): ArrayList<RemoteGameUploadDetails> {
        val results
                = realm.where(LocalGameUploadDetails::class.java).findAll()
        val remoteGameUploads = ArrayList<RemoteGameUploadDetails>()
        results.forEach { localDetail ->
            val gameUploadDetails = RemoteGameUploadDetails(localDetail.pic,
                    localDetail.caption,
                    localDetail.description,
                    localDetail.category,
                    localDetail.location)
            remoteGameUploads.add(gameUploadDetails)
        }
        return  remoteGameUploads
    }

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
            GameUpdateType.DISLIKE -> apiService.likePic(photoId, token)
            GameUpdateType.LIKE -> apiService.dislikePic(photoId, token)
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
                    else emitter.onSuccess(false)
                }

            })
        }
    }

}