package accepted.challenge.fenix.com.photogame.Domain.di

import accepted.challenge.fenix.com.photogame.Data.ApiService
import accepted.challenge.fenix.com.photogame.Domain.managers.Constants
import accepted.challenge.fenix.com.photogame.Domain.managers.PrefManager
import accepted.challenge.fenix.com.photogame.R
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Application dagger module
 */
@Module
class NetModule {

    @Provides
    fun providesContext(application: Application): Context = application

    @Provides
    fun providesRealm(): Realm
            = Realm.getDefaultInstance()

    @Provides
    fun providesApiService(context: Context): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.end_point)?: Constants.MOCK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    fun providePrefManager(context: Context): PrefManager =
            PrefManager(providePref(context), provideGson())

    /**
     * Provides default shared pref
     *
     * @param context
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
}