package com.iglesiabethesda.bethesdapp.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesConfig @Inject constructor(@ApplicationContext private val context: Context) {

    private val SHARED_DB_NAME = "Mydtb"
    private val SHARED_USER_NAME = "userName"
    private val SHARED_USER_UID = "userUid"
    private val SHARED_MEMBER_NAME = "memberName"
    private val SHARED_MEMBER_UID = "memberUId"
    private val SHARED_EMAIL = "email"
    private val SHARED_USER_TYPE = "userType"
    private val SHARED_GENERO = "userGenero"
    private val SHARED_USER_BIRTHDAY = "userBirthDay"
    private val SHARED_USER_HOBBY = "UserHobby"
    private val SHARED_USER_JOB = "UserJob"

    private val storage = context.getSharedPreferences(SHARED_DB_NAME, 0)

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

    fun saveGenero(genero: String) {
        storage.edit().putString(SHARED_GENERO, genero).apply()
    }

    fun saveBirthDay(birthDay: String) {
        storage.edit().putString(SHARED_USER_BIRTHDAY, birthDay).apply()
    }

    fun saveHobby(hobby: String) {
        storage.edit().putString(SHARED_USER_HOBBY, hobby).apply()
    }

    fun saveJob(job: String) {
        storage.edit().putString(SHARED_USER_JOB, job).apply()
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

    fun getGenero(): String {
        return storage.getString(SHARED_GENERO, "")!!
    }

    fun getBirthDay(): String {
        return storage.getString(SHARED_USER_BIRTHDAY, "")!!
    }

    fun getHobby(): String {
        return storage.getString(SHARED_USER_HOBBY, "")!!
    }

    fun getJob(): String {
        return storage.getString(SHARED_USER_JOB, "")!!
    }

    fun clearShredPref() {
        storage.edit().clear().apply()
    }

}