package com.omoda.overlaylauncher.di

import android.content.Context
import com.omoda.overlaylauncher.data.car.CarPropertyVehicleDataSource
import com.omoda.overlaylauncher.data.car.HardKeyDataSource
import com.omoda.overlaylauncher.data.car.HardKeyDataSourceImpl
import com.omoda.overlaylauncher.data.car.VehicleDataSource
import com.omoda.overlaylauncher.data.repository.HardKeyRepositoryImpl
import com.omoda.overlaylauncher.data.repository.VehicleRepositoryImpl
import com.omoda.overlaylauncher.domain.repository.HardKeyRepository
import com.omoda.overlaylauncher.domain.repository.VehicleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideVehicleDataSource(@ApplicationContext context: Context): VehicleDataSource {
        return CarPropertyVehicleDataSource.createWithFallback(context)
    }

    @Provides
    @Singleton
    fun provideVehicleRepository(
        dataSource: VehicleDataSource
    ): VehicleRepository {
        return VehicleRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideHardKeyDataSource(@ApplicationContext context: Context): HardKeyDataSource {
        return HardKeyDataSourceImpl(context)
    }

    @Provides
    @Singleton
    fun provideHardKeyRepository(
        dataSource: HardKeyDataSource
    ): HardKeyRepository {
        return HardKeyRepositoryImpl(dataSource)
    }
}
