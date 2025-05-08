package com.iglesiabethesta.bethestaapp.util

import android.content.Context

class SharedPreferencesConfig(val context: Context) {

    val SHARED_DB_NAME = "Mydtb"
    val SHARED_USER_NAME = "userName"
    val SHARED_USER_TYPE = "userType"

    val storage = context.getSharedPreferences(SHARED_DB_NAME, 0)

    fun saveUserName(name: String) {
        storage.edit().putString(SHARED_USER_NAME, name).apply()
    }

    fun saveUserType(userType: String) {
        storage.edit().putString(SHARED_USER_TYPE, userType).apply()
    }

    fun getUserName(): String {
        return storage.getString(SHARED_USER_NAME, "")!!
    }

    fun getUserType(): String {
        return storage.getString(SHARED_USER_TYPE, "")!!
    }

    fun clearShredPref() {
        storage.edit().clear().apply()
    }

}