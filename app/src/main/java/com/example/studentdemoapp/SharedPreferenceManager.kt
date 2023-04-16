package com.example.studentdemoapp

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceManager {
  private const val PREF_NAME = "StudentDemoApp"
  private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
  private const val KEY_REMIND_ME = "key_remind_me"
  private const val KEY_LAST_LOGIN_EMAIL = "key_last_login_email"

  private lateinit var preferences: SharedPreferences

  fun init(context: Context) {
    preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
  }

  fun isLogin(): Boolean {
    return preferences.getBoolean(KEY_IS_LOGGED_IN, false)
  }

  fun setUserLoggedIn() {
    preferences.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()
  }

  fun setUserLoggedOut() {
    preferences.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply()
  }

  fun saveRemindMe(boolean: Boolean) {
    preferences.edit().putBoolean(KEY_REMIND_ME, boolean).apply()
  }

  fun isRemindMe(): Boolean {
    return preferences.getBoolean(KEY_REMIND_ME, false)
  }

  fun saveLastLoginUser(email: String) {
    preferences.edit().putString(KEY_LAST_LOGIN_EMAIL, email).apply()
  }

  fun getLastLoginEmail(): String? {
    return preferences.getString(KEY_LAST_LOGIN_EMAIL, "")
  }
}