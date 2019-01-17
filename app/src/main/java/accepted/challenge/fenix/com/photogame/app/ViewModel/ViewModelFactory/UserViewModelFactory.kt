package accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory

import accepted.challenge.fenix.com.photogame.Data.repository.UserRepository
import accepted.challenge.fenix.com.photogame.app.ViewModel.UserViewModel
import accepted.challenge.fenix.com.photogame.app.app
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context

class UserViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(UserRepository(
                    app.api(context),
                    app.providePrefManager(context),
                    app.getRealm())) as T
        } else throw IllegalStateException("Class not found")
    }
}