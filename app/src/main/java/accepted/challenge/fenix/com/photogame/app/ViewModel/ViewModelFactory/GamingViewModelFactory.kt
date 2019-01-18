package accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.app.ViewModel.GamingViewModel
import accepted.challenge.fenix.com.photogame.app.app
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context

class GamingViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GamingViewModel::class.java)) {
            return GamingViewModel(GamingRepository(
                    app.api(context),
                    app.getRealm(),
                    app.providePrefManager(context))) as T
        } else throw IllegalStateException("Class not found")
    }
}