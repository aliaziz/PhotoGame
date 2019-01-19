package accepted.challenge.fenix.com.photogame

import accepted.challenge.fenix.com.photogame.Domain.areCredsValid
import accepted.challenge.fenix.com.photogame.Domain.isEmailValid
import accepted.challenge.fenix.com.photogame.app.ViewModel.UserViewModel
import android.arch.lifecycle.ViewModelProviders
import org.junit.Test

import org.junit.Assert.*

class PhotoGameUnitTest {

    @Test
    fun validatesCreds() {
        val name = "ali"
        val email = "email"
        assertTrue(areCredsValid(name, email))
    }

    @Test
    fun validatesWrongFields() {
        assertFalse(areCredsValid(""))
    }

    @Test
    fun validatesEmail() {
        assertTrue(isEmailValid("a@c.com"))
        assertFalse(isEmailValid("abcom"))
    }

    fun loginSuccess() {
    }
}
