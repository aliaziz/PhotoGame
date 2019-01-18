package accepted.challenge.fenix.com.photogame.Data.repository

import accepted.challenge.fenix.com.photogame.Data.ApiService
import accepted.challenge.fenix.com.photogame.Data.model.ApiMessageResponse
import accepted.challenge.fenix.com.photogame.Domain.Constants
import accepted.challenge.fenix.com.photogame.app.Models.GameUploadDetails
import io.reactivex.Single
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GamingRepository(private val apiService: ApiService,
                       private val realm: Realm) {

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
                    gameUploadDetails.description?: "",
                    gameUploadDetails.category?: "",
                    gameUploadDetails.location?: ""
            ).enqueue(object: Callback<ApiMessageResponse> {
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