package com.iglesiabethesda.bethesdapp.events.domain.usecase

import com.iglesiabethesda.bethesdapp.events.data.network.NetworkEventsService
import com.iglesiabethesda.bethesdapp.events.domain.model.EventModel
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class NewEventUseCase @Inject constructor(
    private val eventsService: NetworkEventsService
) {

    suspend operator fun invoke(event: EventModel): Result<Boolean> {
        return kotlin.runCatching {
            eventsService.createEvent(event)
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error al crear evento: ${event.titleEvent}"
            )
        }
    }

}