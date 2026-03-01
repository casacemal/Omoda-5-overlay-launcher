package com.omoda.overlaylauncher.domain.usecase

import com.omoda.overlaylauncher.data.model.VehicleState
import com.omoda.overlaylauncher.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVehicleStateUseCase @Inject constructor(
    private val repository: VehicleRepository
) {
    operator fun invoke(): Flow<VehicleState> {
        return repository.vehicleState
    }
}
