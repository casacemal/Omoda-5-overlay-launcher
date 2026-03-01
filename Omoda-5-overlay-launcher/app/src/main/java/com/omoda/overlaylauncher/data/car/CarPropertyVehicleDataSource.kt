package com.omoda.overlaylauncher.data.car

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import com.omoda.overlaylauncher.data.mock.MockVehicleDataSource
import com.omoda.overlaylauncher.data.model.VehicleState
import com.omoda.overlaylauncher.util.AppLogger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CarPropertyVehicleDataSource private constructor(
    private val carPropertyManager: CarPropertyManager,
) : VehicleDataSource {

    override val vehicleState: Flow<VehicleState> = callbackFlow {
        var latest = VehicleState()

        val callback = object : CarPropertyManager.CarPropertyEventCallback {
            override fun onChangeEvent(value: CarPropertyValue<*>) {
                latest = latest.reduce(value)
                trySend(latest)
            }

            override fun onErrorEvent(propertyId: Int, areaId: Int) {
                AppLogger.d("CarProperty error pid=$propertyId area=$areaId")
            }
        }

        val properties = listOf(
            VehiclePropertyIds.PERF_VEHICLE_SPEED,
            VehiclePropertyIds.ENGINE_RPM,
            VehiclePropertyIds.PERF_ODOMETER,
            VehiclePropertyIds.FUEL_LEVEL,
            VehiclePropertyIds.RANGE_REMAINING,
            VehiclePropertyIds.ENV_OUTSIDE_TEMPERATURE,
            VehiclePropertyIds.DOOR_POS,
            VehiclePropertyIds.DOOR_LOCK,
        )

        runCatching {
            properties.forEach { propertyId ->
                carPropertyManager.registerCallback(
                    callback,
                    propertyId,
                    CarPropertyManager.SENSOR_RATE_ONCHANGE,
                )
            }
        }.onFailure {
            AppLogger.d("CarProperty callback registration failed. ${it.message}")
            close(it)
            return@callbackFlow
        }

        awaitClose {
            properties.forEach { propertyId ->
                runCatching {
                    carPropertyManager.unregisterCallback(callback, propertyId)
                }
            }
        }
    }

    companion object {
        fun createWithFallback(context: Context): VehicleDataSource {
            return runCatching {
                val car = Car.createCar(context)
                val manager = car.getCarManager(Car.PROPERTY_SERVICE) as? CarPropertyManager
                    ?: error("CarPropertyManager unavailable")
                CarPropertyVehicleDataSource(manager)
            }.onFailure {
                AppLogger.d("Car service unavailable or permission denied. Falling back to mock.")
            }.getOrElse {
                MockVehicleDataSource()
            }
        }
    }
}

private fun VehicleState.reduce(value: CarPropertyValue<*>): VehicleState {
    return when (value.propertyId) {
        VehiclePropertyIds.PERF_VEHICLE_SPEED -> copy(speedKph = (value.value as? Float) ?: speedKph)
        VehiclePropertyIds.ENGINE_RPM -> copy(rpm = (value.value as? Float) ?: rpm)
        VehiclePropertyIds.PERF_ODOMETER -> copy(odometerKm = (value.value as? Float) ?: odometerKm)
        VehiclePropertyIds.FUEL_LEVEL -> copy(fuelPercent = (value.value as? Float) ?: fuelPercent)
        VehiclePropertyIds.RANGE_REMAINING -> copy(rangeKm = (value.value as? Float) ?: rangeKm)
        VehiclePropertyIds.ENV_OUTSIDE_TEMPERATURE -> copy(outsideTempC = (value.value as? Float) ?: outsideTempC)
        VehiclePropertyIds.DOOR_POS -> {
            val doorPos = (value.value as? Int) ?: return this
            copy(
                doorPositionByAreaId = doorPositionByAreaId + (value.areaId to doorPos),
                trunkAreaId = value.areaId,
            )
        }

        VehiclePropertyIds.DOOR_LOCK -> {
            val doorLock = ((value.value as? Int) ?: 0) == 1
            copy(doorLockByAreaId = doorLockByAreaId + (value.areaId to doorLock))
        }

        else -> this
    }
}
