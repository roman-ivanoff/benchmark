package com.romanivanov.benchmark.di;

import com.romanivanov.benchmark.models.assembly.Assembly;
import com.romanivanov.benchmark.models.assembly.CollectionsAssembly;
import com.romanivanov.benchmark.models.assembly.MapsAssembly;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    @Named("collections")
    Assembly provideCollectionsAssembly() {
        return new CollectionsAssembly();
    }

    @Singleton
    @Provides
    @Named("maps")
    Assembly provideMapsAssembly() {
        return new MapsAssembly();
    }
}
