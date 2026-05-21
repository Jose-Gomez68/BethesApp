package com.iglesiabethesda.bethesdapp.group.data.network

import android.util.Log
import com.iglesiabethesda.bethesdapp.data.network.FirebaseClient
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModelFirebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NetworkGroupService @Inject constructor(
    private val firebase: FirebaseClient
) {

    companion object {
        const val GROUP_COLLECTION = "group"
    }

    suspend fun createGroupTable(group: GroupModel): Boolean {
        val collection = firebase.db.collection(GROUP_COLLECTION)

        val docRef = collection.document()
        val uid = docRef.id

        val user = hashMapOf(
            "uid" to uid,
            "name" to group.name,
            "description" to group.description,
            "listMembers" to group.listMembers,
            "statusGroup" to group.statusGroup,
            "createdDate" to group.createdDate,
            "updateDate" to group.updateDate,
            "endDate" to group.endDate
        )

        docRef.set(user).await()

        return true
    }


    suspend fun getGroupList() : List<GroupModel> {
        val query = firebase.db.collection(GROUP_COLLECTION)
            .orderBy("name") // orda alfabeticamente
            .get()
            .await()

        return query.documents.mapNotNull { it.toObject(GroupModelFirebase::class.java)?.toModel() }
            .filter { it.statusGroup != 3 }//trae todos menos a los eliminados
    }

    suspend fun deleteGroup(uid: String): Boolean {
        return try {
            firebase.db.collection(GROUP_COLLECTION)
                .document(uid)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateGroupById(group: GroupModel): Boolean = runCatching {

        val collection = firebase.db.collection(GROUP_COLLECTION)

        val uid = group.uid ?: return@runCatching false

        val updateGroup = hashMapOf<String, Any?>(
            "uid" to uid,
            "name" to group.name,
            "description" to group.description,
            "listMembers" to group.listMembers,
            "statusGroup" to group.statusGroup,
            "createdDate" to group.createdDate,
            "updateDate" to group.updateDate,
            "endDate" to group.endDate
        )

        collection.document(uid).update(updateGroup).await()

    }.isSuccess

    suspend fun getGroupById(uid: String): Result<GroupModel> {
        return runCatching {
            val document = firebase.db.collection(GROUP_COLLECTION)
                .document(uid)
                .get()
                .await()

            document.toObject(GroupModelFirebase::class.java)
                ?.toModel()
                ?: throw Exception("Grupo no encontrado")
        }
    }

    suspend fun getGroupByUidUser(uidUser: String): Result<List<GroupModel>> =
        runCatching {
            val cleanUid = uidUser.trim()

            Log.e("FIRESTORE_DEBUG", "UID enviado: '$cleanUid'")

            val snapshot = firebase.db.collection(GROUP_COLLECTION)
                .whereArrayContains("listMembers", cleanUid)
                .get()
                .await()

            Log.e("FIRESTORE_DEBUG", "Docs encontrados: ${snapshot.size()}")

            snapshot.documents.mapNotNull { doc ->
                Log.e("FIRESTORE_DEBUG", "Doc: ${doc.data}")
                doc.toObject(GroupModelFirebase::class.java)?.toModel()
            }.filter { it.statusGroup != 3 }
        }


}