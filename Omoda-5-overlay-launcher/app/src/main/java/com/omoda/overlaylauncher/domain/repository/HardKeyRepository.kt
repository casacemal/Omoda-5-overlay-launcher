package com.omoda.overlaylauncher.domain.repository

import kotlinx.coroutines.flow.Flow

interface HardKeyRepository {
    val hardKeyEvents: Flow<String>
}
