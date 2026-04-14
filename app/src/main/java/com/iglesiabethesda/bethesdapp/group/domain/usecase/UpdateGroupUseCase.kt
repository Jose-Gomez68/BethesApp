package com.iglesiabethesda.bethesdapp.group.domain.usecase

import com.iglesiabethesda.bethesdapp.group.data.network.NetworkGroupService
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import javax.inject.Inject

class UpdateGroupUseCase @Inject constructor(
    private val groupService: NetworkGroupService
) {

    suspend operator fun invoke (group : GroupModel): Boolean {

        val updateGroup = groupService.updateGroupById(group)
        return if (updateGroup){
            true
        }else {
            false
        }

    }

}