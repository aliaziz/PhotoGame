package accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory

import accepted.challenge.fenix.com.photogame.Data.repository.UserRepository
import accepted.challenge.fenix.com.photogame.app.ViewModel.UserViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class UserViewModelFactory @Inject constructor(private val userRepository: UserRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository) as T
        } else throw IllegalStateException("Class not found")
    }
}