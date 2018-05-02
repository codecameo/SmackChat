package tutorial.kotlin.udemy.kotlinchat

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Oshan on 5/2/18.
 */
class SharedPrefs(context: Context) {
    val PREFS_FILENAME = "shared_prefs"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    val IS_LOGGED_IN = "is_logged_in"
    val AUTH_TOKEN = "auth_token"
    val USER_MAIL = "user_mail"

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(IS_LOGGED_IN, value).apply()

    var authToken: String
        get() = prefs.getString(AUTH_TOKEN, "")
        set(value) = prefs.edit().putString(AUTH_TOKEN, value).apply()

    var userMail: String
        get() = prefs.getString(USER_MAIL, "")
        set(value) = prefs.edit().putString(USER_MAIL, value).apply()
}