package com.iglesiabethesda.bethesdapp.group.domain.usecase

import com.iglesiabethesda.bethesdapp.group.data.network.NetworkGroupService
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import javax.inject.Inject

class GetGroupByIdUseCase@Inject constructor(
    private val groupService: NetworkGroupService
) {

    suspend operator fun invoke(uid: String): Result<GroupModel> {
        return groupService.getGroupById(uid)
            .onFailure { e ->
                // aquí puedes loguear
                println("Error UseCase GetGroupByIdUseCase: ${e.message}")
            }
    }

}