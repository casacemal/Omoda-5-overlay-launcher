package com.omoda.overlaylauncher.data.car

import android.content.Context
import com.omoda.overlaylauncher.data.mock.MockVehicleDataSource
import com.omoda.overlaylauncher.data.model.VehicleState
import com.omoda.overlaylauncher.util.AppLogger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.reflect.Proxy

class CarPropertyVehicleDataSource private constructor(
    private val carPropertyManager: Any,
) : VehicleDataSource {

    override val vehicleState: Flow<VehicleState> = callbackFlow {
        var latest = VehicleState()
        val callback = ReflectionBridge.newEventCallback { propertyId, areaId, value ->
            latest = latest.reduce(propertyId, areaId, value)
            trySend(latest)
        }

        runCatching {
            ReflectionBridge.SUBSCRIBED_PROPERTIES.forEach { propertyId ->
                ReflectionBridge.registerCallback(carPropertyManager, callback, propertyId)
            }
        }.onFailure {
            AppLogger.d("CarProperty callback registration failed. ${it.message}")
            close(it)
            return@callbackFlow
        }

        awaitClose {
            ReflectionBridge.SUBSCRIBED_PROPERTIES.forEach { propertyId ->
                runCatching {
                    ReflectionBridge.unregisterCallback(carPropertyManager, callback, propertyId)
                }
            }
        }
    }

    companion object {
        fun createWithFallback(context: Context): VehicleDataSource {
            return runCatching {
                val manager = ReflectionBridge.createCarPropertyManager(context)
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

private object ReflectionBridge {
    private val carClass by lazy { Class.forName("android.car.Car") }
    private val vehiclePropertyIdsClass by lazy { Class.forName("android.car.VehiclePropertyIds") }
    private val callbackClass by lazy {
        Class.forName("android.car.hardware.property.CarPropertyManager\$CarPropertyEventCallback")
    }

    val PERF_VEHICLE_SPEED by lazy { propertyId("PERF_VEHICLE_SPEED") }
    val ENGINE_RPM by lazy { propertyId("ENGINE_RPM") }
    val PERF_ODOMETER by lazy { propertyId("PERF_ODOMETER") }
    val FUEL_LEVEL by lazy { propertyId("FUEL_LEVEL") }
    val RANGE_REMAINING by lazy { propertyId("RANGE_REMAINING") }
    val ENV_OUTSIDE_TEMPERATURE by lazy { propertyId("ENV_OUTSIDE_TEMPERATURE") }
    val DOOR_POS by lazy { propertyId("DOOR_POS") }
    val DOOR_LOCK by lazy { propertyId("DOOR_LOCK") }

    val SUBSCRIBED_PROPERTIES: List<Int> by lazy {
        listOf(
            PERF_VEHICLE_SPEED,
            ENGINE_RPM,
            PERF_ODOMETER,
            FUEL_LEVEL,
            RANGE_REMAINING,
            ENV_OUTSIDE_TEMPERATURE,
            DOOR_POS,
            DOOR_LOCK,
        )
    }

    fun createCarPropertyManager(context: Context): Any? {
        val car = carClass.getMethod("createCar", Context::class.java).invoke(null, context)
        val propertyService = carClass.getField("PROPERTY_SERVICE").get(null)
        return carClass.getMethod("getCarManager", String::class.java).invoke(car, propertyService)
    }

    fun newEventCallback(onChanged: (propertyId: Int, areaId: Int, value: Any?) -> Unit): Any {
        return Proxy.newProxyInstance(
            callbackClass.classLoader,
            arrayOf(callbackClass),
        ) { _, method, args ->
            when (method.name) {
                "onChangeEvent" -> {
                    val carPropertyValue = args?.firstOrNull() ?: return@newProxyInstance null
                    onChanged(
                        propertyId = readPropertyId(carPropertyValue),
                        areaId = readAreaId(carPropertyValue),
                        value = readValue(carPropertyValue),
                    )
                }

                "onErrorEvent" -> {
                    val propertyId = (args?.getOrNull(0) as? Int) ?: -1
                    val areaId = (args?.getOrNull(1) as? Int) ?: -1
                    AppLogger.d("CarProperty error pid=$propertyId area=$areaId")
                }
            }
            null
        }
    }

    fun registerCallback(carPropertyManager: Any, callback: Any, propertyId: Int) {
        val managerClass = carPropertyManager.javaClass
        val rate = managerClass.getField("SENSOR_RATE_ONCHANGE").getFloat(null)
        managerClass
            .getMethod("registerCallback", callbackClass, Int::class.javaPrimitiveType, Float::class.javaPrimitiveType)
            .invoke(carPropertyManager, callback, propertyId, rate)
    }

    fun unregisterCallback(carPropertyManager: Any, callback: Any, propertyId: Int) {
        carPropertyManager.javaClass
            .getMethod("unregisterCallback", callbackClass, Int::class.javaPrimitiveType)
            .invoke(carPropertyManager, callback, propertyId)
    }

    private fun propertyId(name: String): Int = vehiclePropertyIdsClass.getField(name).getInt(null)
    private fun readPropertyId(carPropertyValue: Any): Int =
        carPropertyValue.javaClass.getMethod("getPropertyId").invoke(carPropertyValue) as Int

    private fun readAreaId(carPropertyValue: Any): Int =
        carPropertyValue.javaClass.getMethod("getAreaId").invoke(carPropertyValue) as Int

    private fun readValue(carPropertyValue: Any): Any? =
        carPropertyValue.javaClass.getMethod("getValue").invoke(carPropertyValue)
}

private fun VehicleState.reduce(propertyId: Int, areaId: Int, value: Any?): VehicleState {
    return when (propertyId) {
        ReflectionBridge.PERF_VEHICLE_SPEED -> copy(speedKph = (value as? Float) ?: speedKph)
        ReflectionBridge.ENGINE_RPM -> copy(rpm = (value as? Float) ?: rpm)
        ReflectionBridge.PERF_ODOMETER -> copy(odometerKm = (value as? Float) ?: odometerKm)
        ReflectionBridge.FUEL_LEVEL -> copy(fuelPercent = (value as? Float) ?: fuelPercent)
        ReflectionBridge.RANGE_REMAINING -> copy(rangeKm = (value as? Float) ?: rangeKm)
        ReflectionBridge.ENV_OUTSIDE_TEMPERATURE -> copy(outsideTempC = (value as? Float) ?: outsideTempC)
        ReflectionBridge.DOOR_POS -> {
            val doorPos = (value as? Int) ?: return this
            copy(
                doorPositionByAreaId = doorPositionByAreaId + (areaId to doorPos),
                trunkAreaId = areaId,
            )
        }

        ReflectionBridge.DOOR_LOCK -> {
            val doorLock = ((value as? Int) ?: 0) == 1
            copy(doorLockByAreaId = doorLockByAreaId + (areaId to doorLock))
        }

        else -> this
    }
}
