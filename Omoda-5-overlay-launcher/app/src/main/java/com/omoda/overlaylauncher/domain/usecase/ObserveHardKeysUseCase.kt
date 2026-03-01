package com.omoda.overlaylauncher.domain.usecase

import com.omoda.overlaylauncher.domain.repository.HardKeyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveHardKeysUseCase @Inject constructor(
    private val repository: HardKeyRepository
) {
    operator fun invoke(): Flow<String> {
        return repository.hardKeyEvents
    }
}
