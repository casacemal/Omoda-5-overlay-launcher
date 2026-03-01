package com.omoda.overlaylauncher.data.car

import com.omoda.overlaylauncher.data.model.VehicleState
import kotlinx.coroutines.flow.Flow

interface VehicleDataSource {
    val vehicleState: Flow<VehicleState>
}
