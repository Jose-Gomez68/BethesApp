package com.iglesiabethesda.bethesdapp.members.data.network

import com.iglesiabethesda.bethesdapp.data.network.FirebaseClient
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModelFirebase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NetworkMembersService @Inject constructor(
    private val firebase: FirebaseClient
){

    companion object {
        const val MEMBERS_COLLECTION = "members"
    }

    /**OBTIENE A TODO LOS MIEBROS
     * EXCEPTO AL DE LA SESION QUE
     * ESTA INICIADA, SI ENVIA EL
     * UID TRAE A TODOS MENOS A ESE UID
     * SI MANDAS NULL TRAE A TODOS*/
    suspend fun getAllMembersListExceptionByUid(uidMember: String?): List<MembersModel> {
        val query = firebase.db.collection(MEMBERS_COLLECTION)
            .orderBy("name") //ordena alfabeticamente
            .get()
            .await()

        return query.documents.mapNotNull { it.toObject(MembersModelFirebase::class.java)?.toModel() }
            .filter { it.uid != uidMember }
            .filter { it.statusAccount != 4 } //trae todos menos a los eliminados
    }

    suspend fun getMembersListByUid(
        uidList: List<String>
    ): List<MembersModel> = coroutineScope {

        if (uidList.isEmpty()) return@coroutineScope emptyList()

        uidList.map { uid ->
            async {
                firebase.db
                    .collection(MEMBERS_COLLECTION)
                    .document(uid)
                    .get()
                    .await()
            }
        }.awaitAll()
            .mapNotNull { snapshot ->
                snapshot.toObject(MembersModelFirebase::class.java)?.toModel()
            }
            .filter { it.statusAccount != 4 }
            .sortedBy { it.name }
    }


}