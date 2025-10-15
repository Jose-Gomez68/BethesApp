package com.iglesiabethesda.bethesdapp.events.domain.usecase

import com.iglesiabethesda.bethesdapp.events.data.network.NetworkEventsService
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class DeleteEventByUidUseCase @Inject constructor(
    private val event: NetworkEventsService
) {

    suspend operator fun invoke(uid: String): Result<Boolean> {
        return kotlin.runCatching {
            event.deleteEventByUId(uid)
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error al eliminar el evento ${uid}"
            )
        }
    }

}