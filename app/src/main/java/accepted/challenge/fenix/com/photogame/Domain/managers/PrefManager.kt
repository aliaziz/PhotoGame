package accepted.challenge.fenix.com.photogame.Domain.managers

import accepted.challenge.fenix.com.photogame.app.Models.User
import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

open class PrefManager @Inject constructor(private val pref: SharedPreferences,
                  private val gson: Gson) {

    private val editor: SharedPreferences.Editor = pref.edit()

    /**
     * Current User
     */
    var user: User?
        get() {
            val userString = pref.getString(USER_DATA, "") ?: ""
            return if (userString.isEmpty()) null else gson.fromJson(userString, User::class.java)
        }
        set(user) {
            editor.putString(USER_DATA, gson.toJson(user, User::class.java)).apply()
        }

    var isLoggedIn: Boolean
        get() {
            return pref.getBoolean(IS_LOGGED_IN, false)
        }
        set(value) = editor.putBoolean(IS_LOGGED_IN, value).apply()

    var hasPendingUpload: Boolean
        get() {
            return pref.getBoolean(HAS_PENDING_UPLOAD, false)
        }
        set(value) = editor.putBoolean(HAS_PENDING_UPLOAD, value).apply()

    companion object {
        const val USER_DATA = "current_user_data"
        const val IS_LOGGED_IN = "is_logged_in"
        const val HAS_PENDING_UPLOAD = "has_pending_upload"
    }
}