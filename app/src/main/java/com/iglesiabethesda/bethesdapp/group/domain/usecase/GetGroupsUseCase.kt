package com.iglesiabethesda.bethesdapp.group.domain.usecase

import com.iglesiabethesda.bethesdapp.group.data.network.NetworkGroupService
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class GetGroupsUseCase @Inject constructor(
    private val service: NetworkGroupService
){

    suspend operator fun invoke(): Result<List<GroupModel>> {
        return runCatching {
            service.getGroupList()
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error is get to list groups: ${e.message}"
            )
        }
    }

}