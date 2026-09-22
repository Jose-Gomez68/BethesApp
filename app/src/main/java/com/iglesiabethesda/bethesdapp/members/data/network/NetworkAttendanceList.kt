package com.iglesiabethesda.bethesdapp.members.data.network

import com.iglesiabethesda.bethesdapp.data.network.FirebaseClient
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupAttendanceListModelFirebase
import com.iglesiabethesda.bethesdapp.members.domain.model.AttendanceListModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NetworkAttendanceList @Inject constructor(
    private val firebase: FirebaseClient
) {

    companion object {
        private const val GROUP_ATTENDANCE_LIST_COLLECTION = "groupAttendanceList"
    }

    suspend fun createAttendanceList(attendanceList: AttendanceListModel): Boolean {
        val collection = firebase.db.collection(GROUP_ATTENDANCE_LIST_COLLECTION)

        val docRef = collection.document()
        val uid = docRef.id

        val list = hashMapOf(
            "uid" to uid,
            "uidGrou" to attendanceList.uidGroup,
            "list" to attendanceList.list,
            "createDate" to attendanceList.createDate,
            "updateDate" to attendanceList.updateDate,

        )

        docRef.set(list).await()

        return false

    }

    suspend fun getGroupAttendaceList(): List<AttendanceListModel> {
        val query = firebase.db.collection(GROUP_ATTENDANCE_LIST_COLLECTION)
            .orderBy("createDate")//checar si esta bien asi llamarlo por fecha
            .get()
            .await()

        return query.documents.mapNotNull { it.toObject(GroupAttendanceListModelFirebase::class.java)?.toModel() }
    }

    suspend fun getGroupAttendaceListByDate(date: String): Result<List<AttendanceListModel>> {
        return runCatching {
            firebase.db
                .collection(GROUP_ATTENDANCE_LIST_COLLECTION)
                .whereEqualTo("date", date)
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    document
                        .toObject(GroupAttendanceListModelFirebase::class.java)
                        ?.toModel()
                }
        }
    }

    suspend fun getGroupAttendanceListByGroupId(
        groupUid: String,
        date: String
    ): Result<List<AttendanceListModel>> {
        return runCatching {
            firebase.db
                .collection(GROUP_ATTENDANCE_LIST_COLLECTION)
                .whereEqualTo("groupUid", groupUid)
                .whereEqualTo("date", date)
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    document
                        .toObject(GroupAttendanceListModelFirebase::class.java)
                        ?.toModel()
                }
        }
    }

}