package ar.edu.itba.tpHciMobile.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun loadAuthToken(): String? {
        return preferences.getString(AUTH_TOKEN, null)
    }

    fun removeAuthToken() {
        val editor = preferences.edit()
        editor.remove(AUTH_TOKEN)
        editor.apply()
    }

    fun saveAuthToken(token: String) {
        val editor = preferences.edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    fun loadEmail(): String? {
        return preferences.getString(EMAIL, null)
    }

    fun removeEmail() {
        val editor = preferences.edit()
        editor.remove(EMAIL)
        editor.apply()
    }

    fun saveEmail(email: String?) {
        val editor = preferences.edit()
        editor.putString(EMAIL, email)
        editor.apply()
    }

    companion object {
        const val PREFERENCES_NAME = "my_app"
        const val AUTH_TOKEN = "auth_token"
        const val EMAIL = "email"
    }
}