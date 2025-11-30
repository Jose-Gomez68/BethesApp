package com.iglesiabethesda.bethesdapp.group.domain.usecase

import com.iglesiabethesda.bethesdapp.group.data.network.NetworkGroupService
import com.iglesiabethesda.bethesdapp.group.domain.model.GroupModel
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class GroupRegisterUseCase @Inject constructor(
    private val groupService: NetworkGroupService
){

    suspend operator fun invoke(group: GroupModel): Result<Boolean> {
        return try {
            val result = groupService.createGroupTable(group)
            if (result) {
                Result.success(true)
            } else {
                Result.failure(Exception("Error al crear grupo en Firebase"))
            }
        } catch (e: Exception) {
            crashLytics(
                exception = e,
                message = "Error create New Group: ${e.message}"
            )
            Result.failure(e)
        }
    }

}