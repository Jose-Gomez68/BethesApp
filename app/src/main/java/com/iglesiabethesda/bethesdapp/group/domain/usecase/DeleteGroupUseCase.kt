package com.iglesiabethesda.bethesdapp.group.domain.usecase

import com.iglesiabethesda.bethesdapp.group.data.network.NetworkGroupService
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class DeleteGroupUseCase @Inject constructor(
    private val service: NetworkGroupService
){

    suspend operator fun invoke(uid: String): Result<Boolean> {

        return runCatching {
            service.deleteGroup(uid)
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error to delete Group with uid: ${uid}"
            )
        }

    }

}