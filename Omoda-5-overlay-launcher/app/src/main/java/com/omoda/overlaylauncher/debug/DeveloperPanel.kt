package com.omoda.overlaylauncher.debug

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.omoda.overlaylauncher.data.model.VehicleState

@Composable
fun DeveloperPanel(
    vehicleState: VehicleState,
    hardKeyHistory: List<String>,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Live Sensor Values", style = MaterialTheme.typography.titleMedium)
        Text("speed: ${vehicleState.speedKph} km/h")
        Text("rpm: ${vehicleState.rpm}")
        Text("odometer: ${vehicleState.odometerKm} km")
        Text("fuel: ${vehicleState.fuelPercent}%")
        Text("range: ${vehicleState.rangeKm} km")
        Text("outside temp: ${vehicleState.outsideTempC}°C")
        Text("door pos: ${vehicleState.doorPositionByAreaId}")
        Text("door lock: ${vehicleState.doorLockByAreaId}")
        Text("trunk areaId: ${vehicleState.trunkAreaId}")

        Text(
            text = "\nLast hardkey events",
            style = MaterialTheme.typography.titleMedium,
        )
        hardKeyHistory.forEach { Text("• $it") }

        Text(
            text = "\nManual ADB/Shell commands (reference only)",
            style = MaterialTheme.typography.titleMedium,
        )
        Text("adb shell cmd car_service get-property 0x11600207 0")
        Text("adb shell cmd car_service get-property 0x11600305 0")
        Text("adb shell dumpsys car_service")
        Text("adb shell dumpsys activity service com.android.car")
    }
}
