package com.iglesiabethesda.bethesdapp.data.network

import com.iglesiabethesda.bethesdapp.Login.ui.model.UserSignIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*https://www.youtube.com/watch?v=vGeiA3mmKvw
* https://github.com/ArisGuimera/FirebaseLogin/tree/master/app/src/main
* */
class UserService @Inject constructor(private val firebase: FirebaseClient) {

    companion object {
        const val  USER_COLLECTION = "users"
    }

    suspend fun createUserTable(userSignIn: UserSignIn) = runCatching {
        val user = hashMapOf(
            "email" to userSignIn.email,
            "nickname" to userSignIn.nickName,
            "realname" to userSignIn.realName
        )

        firebase.db
            .collection(USER_COLLECTION)
            .add(user).await()

    }.isSuccess

}