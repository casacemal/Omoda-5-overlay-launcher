package com.omoda.overlaylauncher.data.mock

import com.omoda.overlaylauncher.data.car.VehicleDataSource
import com.omoda.overlaylauncher.data.model.VehicleState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class MockVehicleDataSource : VehicleDataSource {
    override val vehicleState: Flow<VehicleState> = flow {
        var speed = 20f
        var rpm = 1200f
        var odometer = 14532f
        var fuel = 65f
        var range = 410f
        var temp = 22f

        while (true) {
            speed = (speed + Random.nextFloat() * 8f - 4f).coerceIn(0f, 160f)
            rpm = (rpm + Random.nextFloat() * 500f - 250f).coerceIn(700f, 5000f)
            odometer += speed / 3600f
            fuel = (fuel - Random.nextFloat() * 0.03f).coerceAtLeast(0f)
            range = (fuel * 6.4f).coerceAtLeast(0f)
            temp = (temp + Random.nextFloat() * 0.6f - 0.3f).coerceIn(-10f, 45f)

            emit(
                VehicleState(
                    speedKph = speed,
                    rpm = rpm,
                    odometerKm = odometer,
                    fuelPercent = fuel,
                    rangeKm = range,
                    outsideTempC = temp,
                    doorPositionByAreaId = mapOf(0 to 0, 1 to Random.nextInt(0, 2)),
                    doorLockByAreaId = mapOf(0 to true, 1 to Random.nextBoolean()),
                    trunkAreaId = 100,
                ),
            )

            delay(Random.nextLong(500L, 1001L))
        }
    }
}
