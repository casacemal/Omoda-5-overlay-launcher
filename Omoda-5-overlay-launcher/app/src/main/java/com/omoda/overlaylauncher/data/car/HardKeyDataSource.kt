package com.omoda.overlaylauncher.data.car

import kotlinx.coroutines.flow.Flow

interface HardKeyDataSource {
    val hardKeyEvents: Flow<String>
}
