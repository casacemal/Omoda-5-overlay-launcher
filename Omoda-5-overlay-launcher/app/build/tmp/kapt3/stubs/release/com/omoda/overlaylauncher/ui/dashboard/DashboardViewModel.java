package com.omoda.overlaylauncher.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\tH\u0002J\u000e\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\tJ\u000e\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u0012R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000f\u00a8\u0006\u001d"}, d2 = {"Lcom/omoda/overlaylauncher/ui/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "getVehicleStateUseCase", "Lcom/omoda/overlaylauncher/domain/usecase/GetVehicleStateUseCase;", "observeHardKeysUseCase", "Lcom/omoda/overlaylauncher/domain/usecase/ObserveHardKeysUseCase;", "(Lcom/omoda/overlaylauncher/domain/usecase/GetVehicleStateUseCase;Lcom/omoda/overlaylauncher/domain/usecase/ObserveHardKeysUseCase;)V", "currentPage", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "hardKeyEvents", "", "hardKeyHistory", "Lkotlinx/coroutines/flow/StateFlow;", "getHardKeyHistory", "()Lkotlinx/coroutines/flow/StateFlow;", "lastHardKey", "mediaState", "Lcom/omoda/overlaylauncher/data/model/MediaState;", "uiState", "Lcom/omoda/overlaylauncher/data/model/UiState;", "getUiState", "onHardKey", "", "eventDetail", "setCurrentPage", "page", "updateMedia", "state", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.omoda.overlaylauncher.domain.usecase.ObserveHardKeysUseCase observeHardKeysUseCase = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> currentPage = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.omoda.overlaylauncher.data.model.MediaState> mediaState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> lastHardKey = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> hardKeyEvents = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.omoda.overlaylauncher.data.model.UiState> uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> hardKeyHistory = null;
    
    @javax.inject.Inject
    public DashboardViewModel(@org.jetbrains.annotations.NotNull
    com.omoda.overlaylauncher.domain.usecase.GetVehicleStateUseCase getVehicleStateUseCase, @org.jetbrains.annotations.NotNull
    com.omoda.overlaylauncher.domain.usecase.ObserveHardKeysUseCase observeHardKeysUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.omoda.overlaylauncher.data.model.UiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getHardKeyHistory() {
        return null;
    }
    
    private final void onHardKey(java.lang.String eventDetail) {
    }
    
    public final void setCurrentPage(@org.jetbrains.annotations.NotNull
    java.lang.String page) {
    }
    
    public final void updateMedia(@org.jetbrains.annotations.NotNull
    com.omoda.overlaylauncher.data.model.MediaState state) {
    }
}