package com.iglesiabethesda.bethesdapp.members.domain.usecase

import com.iglesiabethesda.bethesdapp.members.data.network.NetworkMembersService
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class GetMembersListUseCase@Inject constructor(
    private val service: NetworkMembersService
) {

    suspend operator fun invoke(uidMember: String?): Result<List<MembersModel>> {
        return runCatching {
            service.getMembersList(uidMember)
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error al obtener el listado de miembros: ${e.message}"
            )
        }
    }

}