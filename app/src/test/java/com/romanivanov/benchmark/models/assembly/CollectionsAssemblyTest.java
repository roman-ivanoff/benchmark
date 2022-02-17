package com.romanivanov.benchmark.models.assembly;

import com.romanivanov.benchmark.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CollectionsAssemblyTest {

    Assembly assembly;

    @Before
    public void setup() {
        assembly = new CollectionsAssembly();
    }

    @Test
    public void createListTest() {
        List<AssemblyData> list = assembly.createList(false);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 21);

        Assert.assertTrue(containsLabelId(list, R.string.arraylist));
        Assert.assertTrue(containsLabelId(list, R.string.linkedlist));
        Assert.assertTrue(containsLabelId(list, R.string.copylist));

        Assert.assertTrue(containsOperationNameId(list, R.string.adding_in_the_beginning));
        Assert.assertTrue(containsOperationNameId(list, R.string.adding_in_the_middle));
        Assert.assertTrue(containsOperationNameId(list, R.string.adding_in_the_end));
        Assert.assertTrue(containsOperationNameId(list, R.string.search_by_value));
        Assert.assertTrue(containsOperationNameId(list, R.string.removing_in_the_beginning));
        Assert.assertTrue(containsOperationNameId(list, R.string.removing_in_the_middle));
        Assert.assertTrue(containsOperationNameId(list, R.string.removing_in_the_end));
    }

    @Test
    public void getSpanCountTest() {

        Assert.assertEquals(assembly.getSpanCount(), 3);
    }

    @Test
    public void measureTimeTest() {

        AssemblyData assemblyData = assembly
                .measureTime(new AssemblyData(R.string.arraylist, R.string.adding_in_the_beginning, false), 222);

        Assert.assertNotNull(assemblyData.getTimeInMs());
        Assert.assertEquals(assemblyData.getLabelId(), R.string.arraylist);
        Assert.assertEquals(assemblyData.getOperationNameId(), R.string.adding_in_the_beginning);
        Assert.assertFalse(assemblyData.isInProgress());
    }

    @Test
    public void measureTimeUnsupportedData() {
        Assert.assertThrows("Unknown collection type " + R.string.treemap,
                RuntimeException.class, () -> {
                    assembly.measureTime(new AssemblyData(R.string.treemap, R.string.search_by_value, false), 222);
                });
    }

    private boolean containsLabelId(final List<AssemblyData> list, final int id){
        return list.stream().anyMatch(o -> o.getLabelId() == id);
    }

    private boolean containsOperationNameId(final List<AssemblyData> list, final int id){
        return list.stream().anyMatch(o -> o.getOperationNameId() == id);
    }
}
