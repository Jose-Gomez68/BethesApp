package com.iglesiabethesda.bethesdapp.util

import android.content.Context

class SharedPreferencesConfig(val context: Context) {

    val SHARED_DB_NAME = "Mydtb"
    val SHARED_USER_NAME = "userName"
    val SHARED_USER_UID = "userUid"
    val SHARED_MEMBER_NAME = "memberName"
    val SHARED_MEMBER_UID = "memberUId"
    val SHARED_EMAIL = "email"
    val SHARED_USER_TYPE = "userType"

    val storage = context.getSharedPreferences(SHARED_DB_NAME, 0)

    fun saveUserName(name: String) {
        storage.edit().putString(SHARED_USER_NAME, name).apply()
    }

    fun saveUserUid(uid: String) {
        storage.edit().putString(SHARED_USER_UID, uid).apply()
    }

    fun saveMemberName(name: String) {
        storage.edit().putString(SHARED_MEMBER_NAME, name).apply()
    }

    fun saveMemberUid(uid: String) {
        storage.edit().putString(SHARED_MEMBER_UID, uid).apply()
    }

    fun saveEmail(email: String) {
        storage.edit().putString(SHARED_EMAIL, email).apply()
    }

    fun saveUserType(userType: String) {
        storage.edit().putString(SHARED_USER_TYPE, userType).apply()
    }
//---------------------------------
    fun getUserName(): String {
        return storage.getString(SHARED_USER_NAME, "")!!
    }

    fun getUserType(): String {
        return storage.getString(SHARED_USER_TYPE, "")!!
    }

    fun getUserUid(): String {
        return storage.getString(SHARED_USER_UID, "")!!
    }


    fun getMemberName(): String {
        return storage.getString(SHARED_MEMBER_NAME, "")!!
    }

    fun getMemberUid(): String {
        return storage.getString(SHARED_MEMBER_UID, "")!!
    }

    fun getEmail(): String {
        return storage.getString(SHARED_EMAIL, "")!!
    }

    fun clearShredPref() {
        storage.edit().clear().apply()
    }

}