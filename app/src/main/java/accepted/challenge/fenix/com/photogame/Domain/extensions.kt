package accepted.challenge.fenix.com.photogame.Domain

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import dmax.dialog.SpotsDialog
import java.io.Serializable

fun areCredsValid(vararg editTexts: String): Boolean {
    var isValid = true
    editTexts.forEach {
        if (it.isBlank()) {
            isValid = false
        }
    }
    return isValid
}

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

/**
 * Simple email validator
 * @param email
 *
 * @return [Boolean]
 */
fun isEmailValid(email: String): Boolean =
    (email.contains("@") && email.contains("."))

