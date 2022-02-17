package com.romanivanov.benchmark.models.assembly;

import java.util.List;

public interface Assembly {

    List<AssemblyData> createList(boolean isInProgress);

    int getSpanCount();

    AssemblyData measureTime(AssemblyData item, int size);
}
