package com.romanivanov.benchmark;

import android.app.Application;

import com.romanivanov.benchmark.di.AppComponent;
import com.romanivanov.benchmark.di.AppModule;
import com.romanivanov.benchmark.di.DaggerAppComponent;

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }


    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    public AppComponent getComponent() {
        return component;
    }

    public void setAppComponent(AppComponent component) {
        this.component = component;
    }
}
