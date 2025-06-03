package com.iglesiabethesda.bethesdapp.data.network

import android.util.Log
import com.iglesiabethesda.bethesdapp.Login.ui.model.UserSignIn
import com.iglesiabethesda.bethesdapp.members.data.MembersModel
import com.iglesiabethesda.bethesdapp.members.data.MembersModelFirebase
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*https://www.youtube.com/watch?v=vGeiA3mmKvw
* https://github.com/ArisGuimera/FirebaseLogin/tree/master/app/src/main
* */
class UserService @Inject constructor(private val firebase: FirebaseClient) {

    companion object {
        const val  USER_COLLECTION = "users"
        const val  MEMBER_COLLECTION = "members"
    }


    suspend fun createUserTable(userSignIn: UserSignIn) = runCatching {
        val uid = firebase.auth.currentUser?.uid ?: throw Exception("No UID found")
        val user = hashMapOf(
            "uid" to uid,
            "uidMember" to userSignIn.uidMember,
            "email" to userSignIn.email,
            "nickname" to userSignIn.nickName,
            "realname" to userSignIn.realName,
            "statusAccount" to userSignIn.statusAccount,
            "createdDate" to userSignIn.createdDate,
            "updateDate" to userSignIn.updateDate

        )

        firebase.db.collection(USER_COLLECTION).document(uid).set(user).await()

    }.isSuccess

    suspend fun createMemberTable(membersModel: MembersModel): Boolean = runCatching {
        val collection = firebase.db.collection(MEMBER_COLLECTION)
        var memberCode = UtilsFunctions().generateMemberCode(
            name = membersModel.name,
            apPaterno = membersModel.apPaterno,
            birthDate = membersModel.birthDay
            )

        /**
         * ACTUALIZAR EL CAMPO DE statusAccount
         * CUANDO SE CONFIRME LA CUENTA DESDE EL CORREO */

        // Generas manualmente el ID
        val docRef = collection.document()
        val uid = docRef.id

        // Ahora puedes agregar ese UID como parte del hashMap
        val user = hashMapOf(
            "uid" to uid,
            "memberCode" to memberCode,
            "name" to membersModel.name,
            "apPaterno" to membersModel.apPaterno,
            "apMaterno" to membersModel.apMaterno,
            "hobby" to membersModel.hobby,
            "jop" to membersModel.job,
            "tel" to membersModel.tel,
            "emergencyContact" to membersModel.emergencyContact,
            "email" to membersModel.email,
            "birthDay" to membersModel.birthDay,
            "statusAccount" to membersModel.statusAccount,// creo debe de ir createUserTable
            "createdDate" to membersModel.createdDate,
            "updateDate" to membersModel.updateDate
        )

        // Guardas el documento con ese ID
        docRef.set(user).await()
    }.isSuccess

    suspend fun getMemberByMemberCode(memberCode: String): MembersModel? = kotlin.runCatching {
        Log.e("AQUI ESTA EL CODIGO", "$memberCode")
        val queryGetMember = firebase
            .db
            .collection(MEMBER_COLLECTION)
            .whereEqualTo("membersCode", memberCode)
            .get()
            .await()

        if (!queryGetMember.isEmpty) {
            val document = queryGetMember.documents[0]
            Log.e("AQUI ESTA EL CODIGO", "${document?.data?.get("name")}")
            document.toObject(MembersModelFirebase::class.java)?.toModel()
        } else {
            null
        }
    }.onFailure {
        Log.e("FIREBASE_ERROR", "Fallo al obtener miembro: ${it.message}", it)
    }.getOrNull()


    suspend fun updateMemberEmailByUid(uid: String, newEmail: String): Boolean = runCatching {
        val collection = firebase.db.collection(MEMBER_COLLECTION)

        // Buscar el documento con el UID especificado
        val querySnapshot = collection
            .whereEqualTo("uid", uid)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val document = querySnapshot.documents.first()
            // Actualizar solo el campo "email"
            document.reference.update("email", newEmail).await()
        } else {
            throw Exception("No se encontró un miembro con el UID: $uid")
        }
    }.isSuccess

    suspend fun updateMemberStatusAccountByUid(uid: String, statusAccount: Int): Boolean = runCatching {
        val collection = firebase.db.collection(MEMBER_COLLECTION)

        // Buscar el documento con el UID especificado
        val querySnapshot = collection
            .whereEqualTo("uid", uid)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val document = querySnapshot.documents.first()
            // Actualizar solo el campo "email"
            document.reference.update("statusAccount", statusAccount).await()
        } else {
            throw Exception("No se encontró un miembro con el UID: $uid")
        }
    }.isSuccess

    suspend fun updateUserStatusAccountByUid(uid: String, statusAccount: Int): Boolean = runCatching {
        val collection = firebase.db.collection(USER_COLLECTION)

        // Buscar el documento con el UID especificado
        val querySnapshot = collection
            .whereEqualTo("uid", uid)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val document = querySnapshot.documents.first()
            // Actualizar solo el campo "email"
            document.reference.update("statusAccount", statusAccount).await()
        } else {
            throw Exception("No se encontró un miembro con el UID: $uid")
        }
    }.isSuccess

}