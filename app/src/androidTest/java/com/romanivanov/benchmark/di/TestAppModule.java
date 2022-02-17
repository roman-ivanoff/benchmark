package com.romanivanov.benchmark.di;

import com.romanivanov.benchmark.models.assembly.Assembly;
import com.romanivanov.benchmark.models.assembly.FakeCollectionsAssembly;
import com.romanivanov.benchmark.models.assembly.FakeMapsAssembly;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestAppModule {

    @Singleton
    @Provides
    @Named("collections")
    Assembly provideCollectionsAssembly() {
        return new FakeCollectionsAssembly();
    }

    @Singleton
    @Provides
    @Named("maps")
    Assembly provideMapsAssembly() {
        return new FakeMapsAssembly();
    }
}
