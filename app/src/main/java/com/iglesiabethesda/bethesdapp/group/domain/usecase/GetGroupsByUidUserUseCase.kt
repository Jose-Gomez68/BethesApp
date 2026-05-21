package com.iglesiabethesda.bethesdapp.group.domain.usecase

import com.iglesiabethesda.bethesdapp.group.data.network.NetworkGroupService
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class GetGroupsByUidUserUseCase @Inject constructor(
    private val groupService: NetworkGroupService
) {

    suspend operator fun invoke(uidUser: String): Result<List<GroupModel>> {
        return groupService.getGroupByUidUser(uidUser)
            .onFailure { e ->
                println("Error UseCase GetGroupByIdUseCase: ${e.message}")
                crashLytics(e as Exception,"Error UseCase GetGroupByIdUseCase")
            }
    }


}