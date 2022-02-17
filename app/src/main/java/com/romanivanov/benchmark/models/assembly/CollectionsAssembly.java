package com.romanivanov.benchmark.models.assembly;

import com.romanivanov.benchmark.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CollectionsAssembly implements Assembly {

    @Override
    public List<AssemblyData> createList(boolean isInProgress) {
        final int[] operations = {
                R.string.adding_in_the_beginning,
                R.string.adding_in_the_middle,
                R.string.adding_in_the_end,
                R.string.search_by_value,
                R.string.removing_in_the_beginning,
                R.string.removing_in_the_middle,
                R.string.removing_in_the_end
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
        return new int[]{R.string.arraylist, R.string.linkedlist, R.string.copylist};
    }

    @Override
    public int getSpanCount() {
        return 3;
    }

    @Override
    public AssemblyData measureTime(AssemblyData item, int size) {
        int randomValue = ThreadLocalRandom.current().nextInt(0, size);
        final List<Integer> list;

        if (R.string.arraylist == item.getLabelId()) {
            list = new ArrayList<>(Collections.nCopies(size, 0));
        } else if (R.string.linkedlist == item.getLabelId()) {
            list = new LinkedList<>(Collections.nCopies(size, 0));
        } else if (R.string.copylist == item.getLabelId()) {
            list = new CopyOnWriteArrayList<>(Collections.nCopies(size, 0));
        } else {
            throw new RuntimeException("Unknown collection type " + item.getLabelId());
        }

        list.add(randomValue, randomValue);
        long before = System.currentTimeMillis();

        switch (item.getOperationNameId()) {
            case R.string.search_by_value:
                list.contains(randomValue);
                break;
            case R.string.adding_in_the_beginning:
                list.add(0, randomValue);
                break;
            case R.string.adding_in_the_middle:
                list.add(size/2, randomValue);
                break;
            case R.string.adding_in_the_end:
                list.add(size-1, randomValue);
                break;
            case R.string.removing_in_the_beginning:
                list.remove(0);
                break;
            case R.string.removing_in_the_middle:
                list.remove(size/2);
                break;
            case R.string.removing_in_the_end:
                list.remove(size-1);
                break;
            default:
                throw new RuntimeException("Unknown collection operation " + item.getOperationNameId());
        }

        return new AssemblyData(item.getLabelId(), item.getOperationNameId(), item.isInProgress(), String.valueOf(System.currentTimeMillis()- before));
    }
}
