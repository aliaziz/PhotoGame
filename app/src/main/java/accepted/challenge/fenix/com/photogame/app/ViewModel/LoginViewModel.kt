package accepted.challenge.fenix.com.photogame.app.ViewModel

import accepted.challenge.fenix.com.photogame.Data.repository.UserRepository
import android.arch.lifecycle.ViewModel
import io.reactivex.Single

class LoginViewModel(val userRepository: UserRepository): ViewModel() {

    fun login(userName: String): Single<Boolean> {

    }
}