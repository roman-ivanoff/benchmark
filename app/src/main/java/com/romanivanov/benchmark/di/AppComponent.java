package com.romanivanov.benchmark.di;

import com.romanivanov.benchmark.ui.assembly.AssemblyViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void injectIntoAssemblyFactory(AssemblyViewModelFactory factory);
}