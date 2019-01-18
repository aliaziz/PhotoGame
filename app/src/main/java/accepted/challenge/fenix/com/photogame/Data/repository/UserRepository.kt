package accepted.challenge.fenix.com.photogame.Data.repository

import accepted.challenge.fenix.com.photogame.Data.ApiService
import accepted.challenge.fenix.com.photogame.Data.model.ApiDataResponse
import accepted.challenge.fenix.com.photogame.app.Models.User
import accepted.challenge.fenix.com.photogame.Domain.PrefManager
import accepted.challenge.fenix.com.photogame.app.Models.UserResponse
import io.reactivex.Single
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val api: ApiService,
                     private val prefManager: PrefManager,
                     private val realm: Realm) {

    var isLoggedIn: Boolean
        get() = prefManager.isLoggedIn
        set(value) {
            prefManager.isLoggedIn = value
        }

    var userData: User?
        get() = prefManager.user
        set(value) {
            prefManager.user = value
        }

    /**
     * Login User
     * @param userName
     * @return Single<User>
     */
    fun login(userName: String): Single<Boolean> = makeLogRegCall(api.login(userName))


    /**
     * Sign up User
     * @param userName
     * @param userEmail
     * @return Single<User>
     */
    fun signUp(userName: String, userEmail: String): Single<Boolean> = makeLogRegCall(api.register(userName, userEmail))

    /**
     * User call maker
     *
     * @param call
     *
     * @return [Single]
     */
    private fun makeLogRegCall(call: Call<ApiDataResponse<UserResponse>>): Single<Boolean> {
        return Single.create { emitter ->
            call.enqueue(object : Callback<ApiDataResponse<UserResponse>> {
                override fun onFailure(call: Call<ApiDataResponse<UserResponse>>, t: Throwable) {
                    emitter.onError(t)
                }

                override fun onResponse(call: Call<ApiDataResponse<UserResponse>>, dataResponse: Response<ApiDataResponse<UserResponse>>) {
                    if (dataResponse.isSuccessful) {
                        emitter.onSuccess(true)
                        saveData(dataResponse.body()?.response?.userData!!)

                    } else emitter.onSuccess(false)
                }

            })
        }
    }

    /**
     * Map data from api model response to app model
     *
     * @param user [User]
     *
     */
    private fun saveData(user: User) {
        prefManager.user = user
        prefManager.isLoggedIn = true
    }
}