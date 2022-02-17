package com.romanivanov.benchmark.models.assembly;

import com.romanivanov.benchmark.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MapsAssemblyTest {

    Assembly assembly;

    @Before
    public void setup() {
        assembly = new MapsAssembly();
    }

    @Test
    public void createListTest() {
        List<AssemblyData> list = assembly.createList(false);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 6);

        Assert.assertTrue(containsLabelId(list, R.string.treemap));
        Assert.assertTrue(containsLabelId(list, R.string.hashmap));

        Assert.assertTrue(containsOperationNameId(list, R.string.adding_new));
        Assert.assertTrue(containsOperationNameId(list, R.string.search_by_key));
        Assert.assertTrue(containsOperationNameId(list, R.string.removing));
    }

    @Test
    public void getSpanCountTest() {

        Assert.assertEquals(assembly.getSpanCount(), 2);
    }

    @Test
    public void measureTimeTest() {

        AssemblyData assemblyData = assembly
                .measureTime(new AssemblyData(R.string.treemap, R.string.adding_new, false), 222);

        Assert.assertNotNull(assemblyData.getTimeInMs());
        Assert.assertEquals(assemblyData.getLabelId(), R.string.treemap);
        Assert.assertEquals(assemblyData.getOperationNameId(), R.string.adding_new);
        Assert.assertFalse(assemblyData.isInProgress());
    }

    @Test
    public void measureTimeUnsupportedData() {
        Assert.assertThrows("Unknown collection operation " + R.string.search_by_value,
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
