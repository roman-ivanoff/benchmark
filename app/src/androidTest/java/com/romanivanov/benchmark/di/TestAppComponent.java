package com.romanivanov.benchmark.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestAppModule.class})
public interface TestAppComponent extends AppComponent {
}
