package com.iglesiabethesda.bethesdapp.data.network

import com.iglesiabethesda.bethesdapp.Login.ui.model.UserModel
import com.iglesiabethesda.bethesdapp.Login.ui.model.UserModelFirebase
import com.iglesiabethesda.bethesdapp.Login.ui.model.UserSignIn
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModelFirebase
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
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
            "nickName" to userSignIn.nickName,
            "realName" to userSignIn.realName,
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
            "job" to membersModel.job,
            "tel" to membersModel.tel,
            "address" to membersModel.address,
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

    /*UPDATE MEMBER*/
    suspend fun updateMemberTable(membersModel: MembersModel): Boolean = runCatching {
        val collection = firebase.db.collection(MEMBER_COLLECTION)

        // Aquí el UID ya viene dentro del modelo (el miembro ya existe)
        val uid = membersModel.uid ?: return@runCatching false

        val updatedUser = hashMapOf<String, Any?>(
            "name" to membersModel.name,
            "apPaterno" to membersModel.apPaterno,
            "apMaterno" to membersModel.apMaterno,
            "hobby" to membersModel.hobby,
            "job" to membersModel.job,
            "tel" to membersModel.tel,
            "address" to membersModel.address,
            "emergencyContact" to membersModel.emergencyContact,
            "email" to membersModel.email,
            "birthDay" to membersModel.birthDay,
            "statusAccount" to membersModel.statusAccount, // si lo deseas actualizar
            "updateDate" to membersModel.updateDate
        )

        // Actualiza solo los campos enviados
        collection.document(uid).update(updatedUser).await()
    }.isSuccess


    suspend fun getMemberByMemberCode(memberCode: String): MembersModel? = kotlin.runCatching {
        val queryGetMember = firebase
            .db
            .collection(MEMBER_COLLECTION)
            .whereEqualTo("membersCode", memberCode)
            .get()
            .await()

        if (!queryGetMember.isEmpty) {
            val document = queryGetMember.documents[0]
            document.toObject(MembersModelFirebase::class.java)?.toModel()
        } else {
            null
        }
    }.onFailure {
        crashLytics(
            exception = it as Exception,
            message = "Error al actualizar al obtener al miembro: ${it.message}"
        )
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

    suspend fun deleteMemberByUid(uid: String): Boolean = runCatching {
        val collection = firebase.db.collection(MEMBER_COLLECTION)

        // Buscar el documento con el UID especificado
        val querySnapshot = collection
            .whereEqualTo("uid", uid)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val document = querySnapshot.documents.first()
            // Actualizar solo el campo "email"
            document.reference.update("statusAccount", 4).await()
        } else {
            throw Exception("No se elimino un miembro con el UID: $uid")
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

    suspend fun getMemberByEmail(email: String): MembersModel? = runCatching {
        val query = firebase
            .db
            .collection(MEMBER_COLLECTION)
            .whereEqualTo("email", email)
            .get()
            .await()

        if (!query.isEmpty) {
            val document = query.documents[0]
            document.toObject(MembersModelFirebase::class.java)?.toModel()
        } else {
            null
        }
    }.onFailure {
        crashLytics(
            exception = it as Exception,
            message = "Error al actualizar al obtener al miembro por email: ${it.message}"
        )
    }.getOrNull()


    suspend fun getUserByEmail(email: String): UserModel? = runCatching {
        val query = firebase
            .db
            .collection(USER_COLLECTION)
            .whereEqualTo("email", email)
            .get()
            .await()

        if (!query.isEmpty) {
            val document = query.documents[0]
            document.toObject(UserModelFirebase::class.java)?.toModel()
        } else {
            null
        }
    }.onFailure {
        crashLytics(
            exception = it as Exception,
            message = "Error al actualizar al obtener al usuario por email: ${it.message}"
        )
    }.getOrNull()


}