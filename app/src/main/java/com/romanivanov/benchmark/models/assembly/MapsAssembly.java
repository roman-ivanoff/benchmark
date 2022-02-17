package com.romanivanov.benchmark.models.assembly;

import com.romanivanov.benchmark.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class MapsAssembly implements Assembly {

    @Override
    public List<AssemblyData> createList(boolean isInProgress) {
        final int[] operations = {
                R.string.adding_new, R.string.search_by_key, R.string.removing
        };
        final int[] labels = getLabels();
        final List<AssemblyData> list = new ArrayList<>(operations.length * labels.length);
        for (int label : labels) {
            for (int operation : operations) {
                list.add(new AssemblyData(label, operation, isInProgress));
            }
        }
        return list;
    }

    private int[] getLabels() {
        return new int[]{R.string.treemap, R.string.hashmap};
    }

    @Override
    public int getSpanCount() {
        return 2;
    }

    @Override
    public AssemblyData measureTime(AssemblyData item, int size) {
        final Map<Integer, Integer> map;
        int randomValue = ThreadLocalRandom.current().nextInt(0, size);

        if (R.string.treemap == item.getLabelId()) {
            map = new TreeMap<>();
        } else if (R.string.hashmap == item.getLabelId()) {
            map = new HashMap<>();
        } else {
            throw new RuntimeException("Unknown map type " + item.getLabelId());
        }

        for (int i = 0; i < size; i++) {
            map.put(i, i);
        }

        map.put(randomValue, randomValue);
        long before = System.currentTimeMillis();

        switch (item.getOperationNameId()) {
            case R.string.search_by_key:
                map.containsKey(randomValue);
                break;
            case R.string.adding_new:
                map.put(size-1, randomValue);
                break;
            case R.string.removing:
                map.remove(size-1);
                break;
            default:
                throw new RuntimeException("Unknown map operation " + item.getOperationNameId());
        }

        return new AssemblyData(item.getLabelId(), item.getOperationNameId(), item.isInProgress(), String.valueOf(System.currentTimeMillis()- before));

    }
}
