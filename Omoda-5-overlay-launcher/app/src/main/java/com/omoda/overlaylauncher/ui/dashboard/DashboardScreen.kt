package com.omoda.overlaylauncher.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omoda.overlaylauncher.BuildConfig
import com.omoda.overlaylauncher.data.model.UiState
import com.omoda.overlaylauncher.debug.DeveloperPanel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val hardKeyHistory by viewModel.hardKeyHistory.collectAsStateWithLifecycle()

    Row(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f).padding(16.dp)) {
            MainDashboardContent(uiState)
        }

        if (BuildConfig.DEBUG) {
            Box(modifier = Modifier.weight(1f)) {
                DeveloperPanel(
                    vehicleState = uiState.vehicleState,
                    hardKeyHistory = hardKeyHistory
                )
            }
        }
    }
}

@Composable
fun MainDashboardContent(uiState: UiState) {
    Column {
        Text("Main Dashboard Area", style = MaterialTheme.typography.headlineMedium)
        Text("Current Page: ${uiState.currentPage}", style = MaterialTheme.typography.bodyLarge)
        
        Text(
            text = "\nMedia Info",
            style = MaterialTheme.typography.titleMedium,
        )
        Text("Title: ${uiState.mediaState.title}")
        Text("Artist: ${uiState.mediaState.artist}")
        Text("Playing: ${uiState.mediaState.isPlaying}")
    }
}
