package com.iglesiabethesda.bethesdapp.members.data.network

import com.iglesiabethesda.bethesdapp.data.network.FirebaseClient
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModelFirebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NetworkMembersService @Inject constructor(
    private val firebase: FirebaseClient
){

    companion object {
        const val MEMBERS_COLLECTION = "members"
    }

    suspend fun getMembersList(uidMember: String?): List<MembersModel> {
        val query = firebase.db.collection(MEMBERS_COLLECTION)
            .orderBy("name") //ordena alfabeticamente
            .get()
            .await()

        return query.documents.mapNotNull { it.toObject(MembersModelFirebase::class.java)?.toModel() }
            .filter { it.uid != uidMember }
            .filter { it.statusAccount != 4 } //trae todos menos a los eliminados
    }

}