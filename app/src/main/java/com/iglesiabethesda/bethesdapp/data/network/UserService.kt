package com.iglesiabethesda.bethesdapp.data.network

import com.iglesiabethesda.bethesdapp.Login.ui.model.UserSignIn
import com.iglesiabethesda.bethesdapp.members.data.MembersModel
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*https://www.youtube.com/watch?v=vGeiA3mmKvw
* https://github.com/ArisGuimera/FirebaseLogin/tree/master/app/src/main
* */
class UserService @Inject constructor(private val firebase: FirebaseClient) {

    companion object {
        //const val  USER_COLLECTION = "users"
        const val  USER_COLLECTION = "members"
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

    suspend fun createMemberTable(membersModel: MembersModel): Boolean = runCatching {
        val uid = firebase.auth.currentUser?.uid ?: throw Exception("No UID found")
        val collection = firebase.db.collection(USER_COLLECTION)
        var memberCode = UtilsFunctions().generateMemberCode(
            name = membersModel.name,
            apPaterno = membersModel.apPaterno,
            birthDate = membersModel.birthDay
            )

        // Generas manualmente el ID
       /* val docRef = collection.document()
        val uid = docRef.id*/

        // Ahora puedes agregar ese UID como parte del hashMap
        val user = hashMapOf(
            "uid" to uid,
            "membersCode" to memberCode,
            "name" to membersModel.name,
            "apPaterno" to membersModel.apPaterno,
            "apMaterno" to membersModel.apMaterno,
            "hobby" to membersModel.hobby,
            "jop" to membersModel.job,
            "tel" to membersModel.tel,
            "emergencyContact" to membersModel.emergencyContact,
            "email" to membersModel.email,
            "birthDay" to membersModel.birthDay,
            "statusAccount" to membersModel.statusAccount,
            "createdDate" to membersModel.createdDate,
            "updateDate" to membersModel.updateDate
        )

        // Guardas el documento con ese ID
        //docRef.set(user).await()
        firebase.db.collection(USER_COLLECTION).document(uid).set(user).await()
    }.isSuccess

}