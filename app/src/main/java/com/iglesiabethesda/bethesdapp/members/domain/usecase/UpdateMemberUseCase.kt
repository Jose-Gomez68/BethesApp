package com.iglesiabethesda.bethesdapp.members.domain.usecase

import com.iglesiabethesda.bethesdapp.data.network.UserService
import com.iglesiabethesda.bethesdapp.members.domain.model.MembersModel
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class UpdateMemberUseCase @Inject constructor(
    private val service: UserService
) {

    suspend operator fun invoke(memberModel: MembersModel): Result<Boolean> {
        return runCatching {
            service.updateMemberTable(memberModel)
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error al actualizar el member: ${e.message}"
            )
        }
    }

}