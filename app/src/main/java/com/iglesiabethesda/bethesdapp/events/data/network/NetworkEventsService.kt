package com.iglesiabethesda.bethesdapp.events.data.network

import com.iglesiabethesda.bethesdapp.data.network.FirebaseClient
import com.iglesiabethesda.bethesdapp.events.domain.model.EventFirebaseModel
import com.iglesiabethesda.bethesdapp.events.domain.model.EventModel
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

class NetworkEventsService @Inject constructor(
    private val firebase: FirebaseClient
){

    companion object {
        const val  EVENT_COLLECTION = "Events"
    }

    suspend fun createEvent(event: EventModel) = kotlin.runCatching {


        // Crear referencia con ID automático
        val docRef = firebase.db.collection(EVENT_COLLECTION).document()

        val event = hashMapOf(
            "uidEvent" to docRef.id,
            "dateEvent" to event.dateEvent,
            "titleEvent" to event.titleEvent,
            "descriptionEvent" to event.descriptionEvent,
            "priorityEvent" to event.priorityEvent,
            "userUidBy" to event.userUidBy,
            "memberUidBy" to event.memberUidBy,
            "notifyAllUsers" to event.notifyAllUsers,
            "notifySelectedUsers" to event.notifySelectedUsers,
            "createNameBy" to event.createNameBy,
            "createDate" to event.createDate,
            "updateDate" to event.updateDate,
        )

        // Guardar en Firestore
        docRef.set(event).await()

    }.isSuccess

    suspend fun getEventsForMonth(year: Int, month: Int): List<EventModel> {
        val calStart = Calendar.getInstance().apply {
            clear()
            set(year, month - 1, 1, 0, 0, 0)
        }
        val calEnd = Calendar.getInstance().apply {
            clear()
            set(year, month - 1, 1, 23, 59, 59)
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
        }

        val queryGetEvent = firebase.db.collection(EVENT_COLLECTION)
            .whereGreaterThanOrEqualTo("dateEvent", calStart.time)
            .whereLessThanOrEqualTo("dateEvent", calEnd.time)
            .get()
            .await()

        return queryGetEvent.documents.mapNotNull { it.toObject(EventFirebaseModel::class.java)?.toModel() }
    }

    suspend fun deleteEventByUId(uid: String) = kotlin.runCatching {

        firebase.db.collection(EVENT_COLLECTION)
            .document(uid)
            .delete()
            .await()

    }.isSuccess

}