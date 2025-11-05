package com.iglesiabethesda.bethesdapp.members.domain.usecase

import com.iglesiabethesda.bethesdapp.data.network.UserService
import com.iglesiabethesda.bethesdapp.members.data.MembersModel
import com.iglesiabethesda.bethesdapp.util.CrashlyticsModuleUtil.crashLytics
import javax.inject.Inject

class MemberRegisterUseCase @Inject constructor(
    private val userService: UserService,
) {

    suspend operator fun invoke(membersModel: MembersModel): Result<Boolean> {
        return runCatching {
            userService.createMemberTable(membersModel)
        }.onFailure { e ->
            crashLytics(
                exception = e as Exception,
                message = "Error create New Members: ${e.message}"
            )
        }
    }

}