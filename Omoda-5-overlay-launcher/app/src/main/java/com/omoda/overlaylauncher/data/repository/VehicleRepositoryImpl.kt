package com.omoda.overlaylauncher.data.repository

import com.omoda.overlaylauncher.data.car.VehicleDataSource
import com.omoda.overlaylauncher.data.model.VehicleState
import com.omoda.overlaylauncher.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepositoryImpl @Inject constructor(
    private val vehicleDataSource: VehicleDataSource
) : VehicleRepository {

    override val vehicleState: Flow<VehicleState>
        get() = vehicleDataSource.vehicleState
}
