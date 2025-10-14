package com.iglesiabethesda.bethesdapp.events.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.R
import com.iglesiabethesda.bethesdapp.events.domain.model.EventModel
import com.iglesiabethesda.bethesdapp.events.domain.usecase.NewEventUseCase
import com.iglesiabethesda.bethesdapp.util.SharedPreferencesConfig
import com.iglesiabethesda.bethesdapp.util.UtilsFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NewEventScreenViewModel @Inject constructor(
    private val newEvent: NewEventUseCase,
    private val shraedPref: SharedPreferencesConfig,
    @ApplicationContext private val context: Context
): ViewModel() {

    val userUid = shraedPref.getUserUid()
    val memberUid = shraedPref.getMemberUid()

    var isEventCreated by mutableStateOf(false)
        private set

    var showErrorDialog by mutableStateOf(false)
        private set

    var selectedUsers = mutableStateListOf<String>()
        private set

    var isLoading by mutableStateOf(false)
        private set


    var etDateEvent by mutableStateOf("")
    var etTitleEvent by mutableStateOf("")
    var etDescriptionEvent by mutableStateOf("")
    var etEventPriority by mutableStateOf(0)
    var notifyAllUsers by mutableStateOf(false)

    var etDateEventError by mutableStateOf<String?>(null)
    var etTitleEventError by mutableStateOf<String?>(null)
    var etDescriptionEventError by mutableStateOf<String?>(null)
    var etEventPriorityError by mutableStateOf<String?>(null)



    fun registerEvent() {
        val event = EventModel(
            uidEvent = "",
            dateEvent = UtilsFunctions().parseDateFromString(etDateEvent) ?: Date(),
            titleEvent = etTitleEvent,
            descriptionEvent = etDescriptionEvent,
            priorityEvent = etEventPriority,
            userUidBy = userUid,
            memberUidBy = memberUid,
            notifyAllUsers = notifyAllUsers,
            notifySelectedUsers = selectedUsers.toList() ?: emptyList(),
            createNameBy = shraedPref.getMemberName(),
            createDate = UtilsFunctions().getCurrentDateTime(),
            updateDate = UtilsFunctions().getCurrentDateTime()
        )

        viewModelScope.launch {

            isLoading = true
            val result: Result<Boolean> = newEvent(event)
            isLoading = false

            result.onSuccess {
                isEventCreated = true
                showErrorDialog = false
            }.onFailure {
                isEventCreated = false
                showErrorDialog = true
            }
        }

    }

    fun setUsers(users: List<String>) {
        selectedUsers.clear()
        selectedUsers.addAll(users)
    }

    fun validateForm(): Boolean {
        var isValid = true

        etDateEventError = null
        etTitleEventError = null
        etDescriptionEventError = null
        etEventPriorityError = null

        if (etTitleEvent.isBlank()) {
            etTitleEventError = context.getString(R.string.tv_title_new_event_error)
            isValid = false
        }else if(etDescriptionEvent.isBlank()) {
            etDescriptionEventError = context.getString(R.string.tv_descrip_new_event_error)
            isValid = false
        }else if(etEventPriority == 0) {
            etEventPriorityError = context.getString(R.string.tv_priority_new_event_error)
            isValid = false
        }else if (etDateEvent.isBlank()) {
            etDateEventError = context.getString(R.string.tv_date_new_event_error)
            isValid = false
        }

        return isValid

    }

}