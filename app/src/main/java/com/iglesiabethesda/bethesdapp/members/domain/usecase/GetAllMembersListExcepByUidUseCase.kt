package com.iglesiabethesda.bethesdapp.members.domain.usecase

import com.iglesiabethesda.bethesdapp.members.data.network.NetworkMembersService
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class GetAllMembersListExcepByUidUseCase @Inject constructor(
    private val service: NetworkMembersService
) {

    /**OBTIENE A TODO LOS MIEBROS
     * EXCEPTO AL DE LA SESION QUE
     * ESTA INICIADA, SI ENVIA EL
     * UID TRAE A TODOS MENOS A ESE UID
     * SI MANDAS NULL TRAE A TODOS*/
    suspend operator fun invoke(uidMember: String?): Result<List<MembersModel>> {
        return runCatching {
            service.getAllMembersListExceptionByUid(uidMember)
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error al obtener el listado de miembros: ${e.message}"
            )
        }
    }

}