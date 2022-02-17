package com.romanivanov.benchmark.ui.assembly;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.romanivanov.benchmark.App;
import com.romanivanov.benchmark.models.assembly.Assembly;

import javax.inject.Inject;
import javax.inject.Named;

public class AssemblyViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    @Named("collections")
    Assembly collection;

    @Inject
    @Named("maps")
    Assembly map;

    private final int position;

    public AssemblyViewModelFactory(int position) {
        this.position = position;
        App.getInstance().getComponent().injectIntoAssemblyFactory(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return position == 0 ? (T) new AssemblyViewModel(collection) : (T) new AssemblyViewModel(map);
    }
}
