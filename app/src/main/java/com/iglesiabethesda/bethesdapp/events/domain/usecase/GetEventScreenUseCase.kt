package com.iglesiabethesda.bethesdapp.events.domain.usecase

import com.iglesiabethesda.bethesdapp.events.data.network.NetworkEventsService
import com.iglesiabethesda.bethesdapp.events.domain.model.EventModel
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class GetEventScreenUseCase @Inject constructor(
    private val event: NetworkEventsService
) {

    suspend operator fun invoke(year: Int, month: Int): Result<List<EventModel>> {
        return runCatching {
            event.getEventsForMonth(year, month)
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error al obtener los eventos: ${e.message}"
            )
        }
    }


}