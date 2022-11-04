package com.example.worldresort.preferences

import android.content.Context
import com.example.worldresort.model.User

class PreferencesManager(context: Context) {
    companion object {
        private const val PREFERENCES_FIRST_LAUNCH = "FirstLaunch"
        private const val LAUNCH_KEY = "launch"
        private const val PREFERENCES_NAME_USER = "UseUser"
        private const val ID_KEY = "id"
        private const val NAME_KEY = "name"
        private const val SURNAME_KEY = "surname"
    }
    private val preferencesUser = context
        .getSharedPreferences(PREFERENCES_NAME_USER, Context.MODE_PRIVATE)
    private val preferencesLaunch = context
        .getSharedPreferences(PREFERENCES_FIRST_LAUNCH, Context.MODE_PRIVATE)
    var launch: Boolean
        get() = preferencesLaunch.getBoolean(LAUNCH_KEY, true)
        set(value) = preferencesLaunch
            .edit()
            .putBoolean(LAUNCH_KEY, value)
            .apply()
    private var id: Int
        get() = preferencesUser.getInt(ID_KEY, 0)
        set(value) = preferencesUser
            .edit()
            .putInt(ID_KEY, value)
            .apply()
    private var name: String?
        get() = preferencesUser.getString(NAME_KEY, null) ?: ""
        set(value) = preferencesUser
            .edit()
            .putString(NAME_KEY, value)
            .apply()
    private var surname: String?
        get() = preferencesUser.getString(SURNAME_KEY, null) ?: ""
        set(value) = preferencesUser
            .edit()
            .putString(SURNAME_KEY, value)
            .apply()
    fun signInUser(user: User) {
        id = user.accountId
        name = user.name
        surname = user.surname
    }
    fun getUser() = User(id, name!!, surname!!)
    fun checkUser() = id != 0

    fun clearPreferences() {
        preferencesUser
            .edit()
            .clear()
            .apply()
    }
}