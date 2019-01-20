package accepted.challenge.fenix.com.photogame

import accepted.challenge.fenix.com.photogame.Data.ApiService
import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.Domain.Helpers
import accepted.challenge.fenix.com.photogame.Domain.managers.PrefManager
import accepted.challenge.fenix.com.photogame.app.Models.LeaderShipModel
import accepted.challenge.fenix.com.photogame.app.Models.RemoteGameUploadDetails
import accepted.challenge.fenix.com.photogame.app.ViewModel.GamingViewModel
import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.Single
import io.realm.Realm
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class PhotoGameUnitTest {


    val gamingViewModel = Mockito.mock(GamingViewModel::class.java)

    @Test
    fun validatesCreds() {
        val name = "ali"
        val email = "email"
        assertTrue(Helpers.areCredsValid(name, email))
    }

    @Test
    fun validatesWrongFields() {
        assertFalse(Helpers.areCredsValid(""))
    }

    @Test
    fun validatesEmail() {
        assertTrue(Helpers.isEmailValid("a@c.com"))
        assertFalse(Helpers.isEmailValid("abcom"))
    }
}
