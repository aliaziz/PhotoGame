package accepted.challenge.fenix.com.photogame.Domain

import accepted.challenge.fenix.com.photogame.Domain.managers.Constants
import accepted.challenge.fenix.com.photogame.Domain.managers.ErrorMessages
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Button
import android.widget.Toast
import dmax.dialog.SpotsDialog
import java.io.Serializable

/**
 * Shows loader
 *
 * @param message
 * @return [AlertDialog]
 */
fun Activity.loader(message: String? = null): AlertDialog {
    return SpotsDialog.Builder()
            .setContext(this)
            .setMessage(message)
            .build()
}

/**
 * Hides loader
 *
 * @param loader [AlertDialog]
 */
fun hide(loader: AlertDialog) = loader.dismiss()

/**
 * Convenience method to show toast message
 *
 * @param message
 */
fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Button.disable(disable: Boolean, color: Int) {
    isEnabled = !disable
    if (disable) setBackgroundColor(color)
    else setBackgroundColor(color)
}

fun Activity.moveTo(newActivity: Class<*>, vararg extras: Pair<String, Serializable>?) {
    val intent = Intent(this, newActivity)
    if (!extras.isNullOrEmpty()) {
        extras.forEach { extra ->
            intent.putExtra(extra?.first, extra?.second)
        }
    }
    startActivity(intent)
}

class Helpers {
    companion object {
        /**
         * Simple email validator
         * @param email
         *
         * @return [Boolean]
         */
        fun isEmailValid(email: String): Boolean =
                (email.contains("@") && email.contains("."))

        /**
         * Check for internet connectivity
         *
         * @param context - the application context
         * @return whether device is connected to internet
         */
        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }

        fun areCredsValid(vararg editTexts: String): Boolean {
            var isValid = true
            editTexts.forEach {
                if (it.isBlank()) {
                    isValid = false
                }
            }
            return isValid
        }

        fun message(errorMessages: ErrorMessages): String {
            return when(errorMessages) {
                ErrorMessages.INVALID_CREDENTIALS -> Constants.INVALID_CREDENTIALS
                ErrorMessages.LOAD_ERROR -> Constants.LOAD_ERROR
                ErrorMessages.NO_MORE_DATA -> Constants.NO_MORE_DATA
                ErrorMessages.LIKE_ERROR -> Constants.LIKE_ERROR
                ErrorMessages.DISLIKE_ERROR -> Constants.DISLIKE_ERROR
                ErrorMessages.MISSING_DATA -> Constants.MISSING_DATA
            }
        }
    }
}

