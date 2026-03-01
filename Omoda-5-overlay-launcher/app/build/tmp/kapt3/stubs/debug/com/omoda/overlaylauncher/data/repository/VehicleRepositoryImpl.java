package com.omoda.overlaylauncher.data.repository;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2 = {"Lcom/omoda/overlaylauncher/data/repository/VehicleRepositoryImpl;", "Lcom/omoda/overlaylauncher/domain/repository/VehicleRepository;", "vehicleDataSource", "Lcom/omoda/overlaylauncher/data/car/VehicleDataSource;", "(Lcom/omoda/overlaylauncher/data/car/VehicleDataSource;)V", "vehicleState", "Lkotlinx/coroutines/flow/Flow;", "Lcom/omoda/overlaylauncher/data/model/VehicleState;", "getVehicleState", "()Lkotlinx/coroutines/flow/Flow;", "app_debug"})
public final class VehicleRepositoryImpl implements com.omoda.overlaylauncher.domain.repository.VehicleRepository {
    @org.jetbrains.annotations.NotNull
    private final com.omoda.overlaylauncher.data.car.VehicleDataSource vehicleDataSource = null;
    
    @javax.inject.Inject
    public VehicleRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.omoda.overlaylauncher.data.car.VehicleDataSource vehicleDataSource) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.Flow<com.omoda.overlaylauncher.data.model.VehicleState> getVehicleState() {
        return null;
    }
}