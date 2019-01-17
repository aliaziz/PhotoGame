package accepted.challenge.fenix.com.photogame.app.ViewModel

import accepted.challenge.fenix.com.photogame.Data.repository.UserRepository
import accepted.challenge.fenix.com.photogame.Domain.ErrorMessages
import accepted.challenge.fenix.com.photogame.app.View.HomeActivity
import android.arch.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class UserViewModel(val userRepository: UserRepository): ViewModel() {

    var waitEventsSubscription = PublishSubject.create<Pair<Boolean, Class<*>?>>()
    var toastHandler = PublishSubject.create<ErrorMessages>()

    /**
     * Login User
     *
     * @param userName
     *
     * @return [Single]
     */
    fun login(userName: String): Single<Boolean> = Single.create {  }

    /**
     * Register user
     *
     * @param userName
     * @param userEmail
     *
     * @return [Single]
     */
    fun register(userName: String, userEmail: String): Single<Boolean> = Single.create {  }

    /**
     * Handles success response on logging in
     *
     * @param isLoggedIn
     */
    fun handleSuccess(isLoggedIn: Boolean) {
        if (isLoggedIn)
            waitEventsSubscription.onNext(Pair(false, HomeActivity::class.java))
        else toastHandler.onNext(ErrorMessages.INVALID_CREDENTIALS)
    }

    /**
     * Handle errors on failure
     *
     * @param error
     */
    fun handleFailure(error: Throwable) {
        waitEventsSubscription.onNext(Pair(false, null))
    }
}
