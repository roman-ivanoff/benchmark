package com.romanivanov.benchmark.models.assembly;

import static java.lang.Thread.sleep;

public class FakeMapsAssembly extends MapsAssembly{

    @Override
    public AssemblyData measureTime(AssemblyData item, int size) {

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new AssemblyData(item.getLabelId(), item.getOperationNameId(), item.isInProgress(), "5");
    }
}

