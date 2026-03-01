package com.omoda.overlaylauncher.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class VehicleState(
    val speedKph: Float = 0f,
    val rpm: Float = 0f,
    val odometerKm: Float = 0f,
    val fuelPercent: Float = 0f,
    val rangeKm: Float = 0f,
    val outsideTempC: Float = 0f,
    val doorPositionByAreaId: Map<Int, Int> = emptyMap(),
    val doorLockByAreaId: Map<Int, Boolean> = emptyMap(),
    val trunkAreaId: Int? = null,
)

@Immutable
data class MediaState(
    val title: String = "",
    val artist: String = "",
    val isPlaying: Boolean = false,
)

@Immutable
data class UiState(
    val currentPage: String = "home",
    val vehicleState: VehicleState = VehicleState(),
    val mediaState: MediaState = MediaState(),
    val lastHardKey: String? = null,
)
