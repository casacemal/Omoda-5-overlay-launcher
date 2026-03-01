package com.omoda.overlaylauncher.domain.repository

import com.omoda.overlaylauncher.data.model.VehicleState
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    val vehicleState: Flow<VehicleState>
}
