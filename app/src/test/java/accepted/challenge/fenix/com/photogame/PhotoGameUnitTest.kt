package accepted.challenge.fenix.com.photogame

import accepted.challenge.fenix.com.photogame.Data.ApiService
import accepted.challenge.fenix.com.photogame.Data.repository.GamingRepository
import accepted.challenge.fenix.com.photogame.Domain.Helpers
import accepted.challenge.fenix.com.photogame.Domain.managers.PrefManager
import android.content.Context
import android.preference.PreferenceManager
import android.test.mock.MockContext
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class PhotoGameUnitTest {

    private val context = MockContext()
    private lateinit var gamingRepository: GamingRepository

    @Before
    fun setup() {
        val apiService = Mockito.mock(ApiService::class.java)
        val realmObject = getRealmInstance()
        val gson = provideGson()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val prefManager = PrefManager(sharedPref, gson)
        gamingRepository = GamingRepository(apiService, realmObject, prefManager)
    }

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
        gamingRepository.hasPendingUpload = false
        assertTrue(Helpers.isEmailValid("a@c.com"))
        assertFalse(Helpers.isEmailValid("abcom"))
    }

    private fun getRealmInstance(): Realm {
        Realm.init(context)
        val config = RealmConfiguration.Builder().name("photoGameTest.realm").build()
        Realm.setDefaultConfiguration(config)
        return Realm.getDefaultInstance()
    }

    private fun provideGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }
}
