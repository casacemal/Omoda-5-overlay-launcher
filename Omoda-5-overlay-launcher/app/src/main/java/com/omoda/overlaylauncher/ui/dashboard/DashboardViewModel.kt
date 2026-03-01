package com.omoda.overlaylauncher.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omoda.overlaylauncher.data.model.MediaState
import com.omoda.overlaylauncher.data.model.UiState
import com.omoda.overlaylauncher.domain.usecase.GetVehicleStateUseCase
import com.omoda.overlaylauncher.domain.usecase.ObserveHardKeysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getVehicleStateUseCase: GetVehicleStateUseCase,
    private val observeHardKeysUseCase: ObserveHardKeysUseCase
) : ViewModel() {

    private val currentPage = MutableStateFlow("home")
    private val mediaState = MutableStateFlow(MediaState())
    private val lastHardKey = MutableStateFlow<String?>(null)
    private val hardKeyEvents = MutableStateFlow<List<String>>(emptyList())

    init {
        viewModelScope.launch {
            observeHardKeysUseCase().collect { event ->
                onHardKey(event)
            }
        }
    }

    val uiState: StateFlow<UiState> = combine(
        currentPage,
        getVehicleStateUseCase(),
        mediaState,
        lastHardKey,
    ) { page, vehicle, media, key ->
        UiState(
            currentPage = page,
            vehicleState = vehicle,
            mediaState = media,
            lastHardKey = key,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState())

    val hardKeyHistory: StateFlow<List<String>> = hardKeyEvents

    private fun onHardKey(eventDetail: String) {
        lastHardKey.value = eventDetail
        hardKeyEvents.value = (listOf(eventDetail) + hardKeyEvents.value).take(20)
    }

    fun setCurrentPage(page: String) {
        currentPage.value = page
    }

    fun updateMedia(state: MediaState) {
        mediaState.value = state
    }
}
