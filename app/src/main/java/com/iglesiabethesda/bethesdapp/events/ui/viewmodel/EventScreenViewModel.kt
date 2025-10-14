package com.iglesiabethesda.bethesdapp.events.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iglesiabethesda.bethesdapp.events.domain.model.EventModel
import com.iglesiabethesda.bethesdapp.events.domain.usecase.GetEventScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val eventUseCase: GetEventScreenUseCase
): ViewModel() {

    private val _getEvents = mutableStateOf<Result<List<EventModel>>?>(null)
    val getEvents: State<Result<List<EventModel>>?> = _getEvents

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun getEvents(year: Int, month: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = eventUseCase(year, month)
            _getEvents.value = result

            _isLoading.value = false
        }
    }

}