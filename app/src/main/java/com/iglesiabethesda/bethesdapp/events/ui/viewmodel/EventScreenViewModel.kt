package com.iglesiabethesda.bethesdapp.events.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.data.network.GeneretedTokenMessageFCM
import com.iglesiabethesda.bethesdapp.events.domain.model.EventModel
import com.iglesiabethesda.bethesdapp.events.domain.usecase.DeleteEventByUidUseCase
import com.iglesiabethesda.bethesdapp.events.domain.usecase.GetEventScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val eventUseCase: GetEventScreenUseCase,
    private val deleteEventUseCase: DeleteEventByUidUseCase,
    private val generatedTokenMessageFCM: GeneretedTokenMessageFCM
): ViewModel() {

    private val _getEvents = mutableStateOf<Result<List<EventModel>>?>(null)
    val getEvents: State<Result<List<EventModel>>?> = _getEvents

    private val _getEventsDelete = mutableStateOf<Boolean>(false)
    val getEventsDelete: State<Boolean> = _getEventsDelete

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun getEvents(year: Int, month: Int) {
        generatedTokenMessageFCM.getToken()
        viewModelScope.launch {
            _isLoading.value = true
            val result = eventUseCase(year, month)
            _getEvents.value = result

            _isLoading.value = false
        }
    }

    fun deleteEventByUid(uid:String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = deleteEventUseCase.invoke(uid)
            result.onSuccess {
                _getEventsDelete.value = false
            }.onFailure {
                _getEventsDelete.value = true
                //si falla es true y muestra el mensaje de error
            }
            _isLoading.value = false
        }
    }

}