package com.iglesiabethesda.bethesdapp.members.domain.usecase

import com.iglesiabethesda.bethesdapp.members.data.network.NetworkAttendanceList
import com.iglesiabethesda.bethesdapp.members.domain.model.AttendanceListModel
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class AttendanceListUseCase @Inject constructor(
    private val service: NetworkAttendanceList
) {

    suspend operator fun invoke(list: AttendanceListModel): Result<Boolean> {
        return runCatching {
            service.createAttendanceList(list)
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error al insertar la lista de asistencia del grupo: ${list.uidGroup}"
            )
        }
    }

}