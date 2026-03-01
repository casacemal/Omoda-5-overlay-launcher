package com.omoda.overlaylauncher.data.repository

import com.omoda.overlaylauncher.data.car.HardKeyDataSource
import com.omoda.overlaylauncher.domain.repository.HardKeyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HardKeyRepositoryImpl @Inject constructor(
    private val dataSource: HardKeyDataSource
) : HardKeyRepository {
    override val hardKeyEvents: Flow<String>
        get() = dataSource.hardKeyEvents
}
