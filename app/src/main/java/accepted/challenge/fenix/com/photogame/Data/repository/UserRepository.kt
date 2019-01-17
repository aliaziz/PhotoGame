package accepted.challenge.fenix.com.photogame.Data.repository

import accepted.challenge.fenix.com.photogame.Data.ApiService
import accepted.challenge.fenix.com.photogame.Data.model.LocalUserModel
import accepted.challenge.fenix.com.photogame.Domain.PrefManager
import accepted.challenge.fenix.com.photogame.app.Models.User
import io.reactivex.Single
import io.realm.Realm

class UserRepository(private val api: ApiService,
                     private val prefManager: PrefManager,
                     private val realm: Realm) {
    /**
     * Login user
     * @param userName
     * @return Single<User>
     */
    fun login(userName: String): Single<User>? = null

    /**
     * Sign up user
     * @param userName
     * @param userEmail
     * @return Single<User>
     */
    fun signUp(userName: String, userEmail: String): Single<User>? = null

    private fun saveData() {
        val user = LocalUserModel()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(user)
        realm.commitTransaction()
    }
}