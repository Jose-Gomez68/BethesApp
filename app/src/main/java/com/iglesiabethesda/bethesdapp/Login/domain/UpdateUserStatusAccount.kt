package com.iglesiabethesda.bethesdapp.Login.domain

import com.iglesiabethesda.bethesdapp.data.network.UserService
import javax.inject.Inject

class UpdateUserStatusAccount @Inject constructor(
    private val userService: UserService
) {

    suspend operator fun invoke(userUid:String):Boolean {
        val updateUser = userService.updateUserStatusAccountByUid(userUid,2)
        return if (updateUser) {
            true
        }else{
            false
        }
    }

}