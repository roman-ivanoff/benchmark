package com.romanivanov.benchmark.ui.assembly;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.romanivanov.benchmark.R;
import com.romanivanov.benchmark.models.Resource;
import com.romanivanov.benchmark.models.assembly.Assembly;
import com.romanivanov.benchmark.models.assembly.AssemblyData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AssemblyViewModel extends ViewModel {
    public final MutableLiveData<Resource<List<AssemblyData>>> itemsLiveData = new MutableLiveData<>(Resource.loading(null));
    private final Assembly assembly;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public AssemblyViewModel(Assembly collection) {
        this.assembly = collection;
    }

    public int getSpanCount() {
        return assembly.getSpanCount();
    }

    public void onCreate() {
        itemsLiveData.setValue(Resource.success(assembly.createList(false)));
    }

    public void clear() {
        disposable.clear();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public void onCalculateClicked(String sizeStr) {
        clear();

        int size;
        try {
            size = Integer.parseInt(sizeStr);
        } catch (Throwable e) {
            itemsLiveData.setValue(Resource.error(R.string.enter_correct_size, null));
            return;
        }
        itemsLiveData.setValue(Resource.success(assembly.createList(true)));
        disposable.add(Observable.fromIterable(assembly.createList(false))
                .map(item -> assembly.measureTime(item, size))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    final List<AssemblyData> value = new ArrayList<>(itemsLiveData.getValue().data);
                    final int idx = findIndex(value, item);
                    if (idx != -1) {
                        value.set(findIndex(value, item), item);
                        itemsLiveData.setValue(Resource.success(value));
                    }
                }, Throwable::printStackTrace)
        );
    }

    private int findIndex(List<AssemblyData> value, AssemblyData item) {
        for (int i = 0; i < value.size(); i++) {
            AssemblyData a = value.get(i);
            if (item.isSameItemType(a)) {
                return i;
            }
        }
        return -1;
    }
}
