package com.omoda.overlaylauncher;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = OmodaApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface OmodaApplication_GeneratedInjector {
  void injectOmodaApplication(OmodaApplication omodaApplication);
}
