package lat.pam.clashcoffeeshop

import android.content.Context

object SessionManager {
    private const val PREF = "clash_session"
    private const val KEY_NAME = "name"
    private const val KEY_USERNAME = "username"
    private const val KEY_PASSWORD = "password"

    private const val DEFAULT_NAME = "Pelanggan"
    private const val DEMO_USERNAME = "admin"
    private const val DEMO_PASSWORD = "12345"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun register(context: Context, name: String, username: String, password: String) {
        prefs(context).edit()
            .putString(KEY_NAME, name)
            .putString(KEY_USERNAME, username)
            .putString(KEY_PASSWORD, password)
            .apply()
    }

    fun login(context: Context, username: String, password: String): Boolean {
        val p = prefs(context)
        val savedUser = p.getString(KEY_USERNAME, null)
        val savedPass = p.getString(KEY_PASSWORD, null)
        if (savedUser != null && username == savedUser && password == savedPass) return true
        return username == DEMO_USERNAME && password == DEMO_PASSWORD
    }

    fun getName(context: Context): String =
        prefs(context).getString(KEY_NAME, null) ?: DEFAULT_NAME

    fun setName(context: Context, name: String) {
        prefs(context).edit().putString(KEY_NAME, name).apply()
    }
}
