package com.iglesiabethesda.bethesdapp.members.domain.usecase

import com.iglesiabethesda.bethesdapp.data.network.UserService
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class DeleteMemberUseCase @Inject constructor(
    private val service: UserService
){

    suspend operator fun invoke(uid: String): Result<Boolean> {
        return kotlin.runCatching {
            service.deleteMemberByUid(uid)
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error al eliminar el miembro con uid: ${uid}"
            )
        }
    }

}