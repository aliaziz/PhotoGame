package accepted.challenge.fenix.com.photogame

import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.Domain.Helpers
import accepted.challenge.fenix.com.photogame.app.Models.RemoteGameUploadDetails
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito

class PhotoGameUnitTest {

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
