package accepted.challenge.fenix.com.photogame.app

import accepted.challenge.fenix.com.photogame.Data.ApiService
import accepted.challenge.fenix.com.photogame.Domain.PrefManager
import accepted.challenge.fenix.com.photogame.R
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotoGame: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("photoGame.realm").build()
        Realm.setDefaultConfiguration(config)
    }
    /**
     * Creates api interface
     *
     * @return [ApiService]
     */
    fun api(context: Context): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.end_point))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Shared prefs
     *
     * @return [SharedPreferences]
     */
    private fun providePref(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    /**
     * Provides gson
     *
     * @return [Gson]
     */
    private fun provideGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    /**
     * Provides pref manager
     *
     * @return [PrefManager]
     */
    fun providePrefManager(context: Context): PrefManager =
            PrefManager(providePref(context), provideGson())

    fun getRealm() = Realm.getDefaultInstance()!!
}

val app: PhotoGame = PhotoGame()