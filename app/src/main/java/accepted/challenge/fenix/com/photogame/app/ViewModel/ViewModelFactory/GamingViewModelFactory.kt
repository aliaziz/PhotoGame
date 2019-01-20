package accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.app.ViewModel.GamingViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class GamingViewModelFactory @Inject constructor(private val gamingRepository: GamingRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GamingViewModel::class.java)) {
            return GamingViewModel(gamingRepository) as T
        } else throw IllegalStateException("Class not found")
    }
}