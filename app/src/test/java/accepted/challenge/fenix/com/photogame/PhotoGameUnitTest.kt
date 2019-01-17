package accepted.challenge.fenix.com.photogame

import accepted.challenge.fenix.com.photogame.Domain.areCredsValid
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
}
