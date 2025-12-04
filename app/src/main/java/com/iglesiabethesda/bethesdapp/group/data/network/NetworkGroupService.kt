package com.iglesiabethesda.bethesdapp.group.data.network

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


}