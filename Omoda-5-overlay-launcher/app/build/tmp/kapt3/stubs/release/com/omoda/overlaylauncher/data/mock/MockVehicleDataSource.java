package com.omoda.overlaylauncher.data.mock;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/omoda/overlaylauncher/data/mock/MockVehicleDataSource;", "Lcom/omoda/overlaylauncher/data/car/VehicleDataSource;", "()V", "vehicleState", "Lkotlinx/coroutines/flow/Flow;", "Lcom/omoda/overlaylauncher/data/model/VehicleState;", "getVehicleState", "()Lkotlinx/coroutines/flow/Flow;", "app_release"})
public final class MockVehicleDataSource implements com.omoda.overlaylauncher.data.car.VehicleDataSource {
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<com.omoda.overlaylauncher.data.model.VehicleState> vehicleState = null;
    
    public MockVehicleDataSource() {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.Flow<com.omoda.overlaylauncher.data.model.VehicleState> getVehicleState() {
        return null;
    }
}