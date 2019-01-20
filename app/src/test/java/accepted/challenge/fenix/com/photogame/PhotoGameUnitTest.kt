package accepted.challenge.fenix.com.photogame

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.Domain.areCredsValid
import accepted.challenge.fenix.com.photogame.Domain.isEmailValid
import accepted.challenge.fenix.com.photogame.app.Models.RemoteGameUploadDetails
import accepted.challenge.fenix.com.photogame.app.app
import android.content.Context
import org.junit.Test
import org.mockito.Mockito.*

import org.junit.Assert.*
import org.mockito.Mockito

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

    @Test
    fun savesPendingUploads() {
        val pendingUpload
                = RemoteGameUploadDetails("http://abc.d.png",
                "pending",
                "desc",
                "life",
                "kampala")


        val gameRepo = Mockito.mock(GamingRepository::class.java)
        Mockito.`when`(gameRepo.saveUpload(pendingUpload))

        assert(gameRepo.getSavedUpload().size > 0)
        assertTrue(gameRepo.hasPendingUpload)
    }

}
