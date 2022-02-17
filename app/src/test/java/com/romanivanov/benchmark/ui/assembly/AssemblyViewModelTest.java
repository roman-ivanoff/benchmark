package com.romanivanov.benchmark.ui.assembly;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.romanivanov.benchmark.RxImmediateSchedulerRule;
import com.romanivanov.benchmark.models.Resource;
import com.romanivanov.benchmark.models.assembly.Assembly;
import com.romanivanov.benchmark.models.assembly.AssemblyData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AssemblyViewModelTest {

    @Mock
    Assembly assembly;

    public AssemblyViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new AssemblyViewModel(assembly);
    }

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void onCreateTest() {
        AssemblyData assemblyData = new AssemblyData(1, 1, false);
        List<AssemblyData> list = new ArrayList<AssemblyData>() {{
            add(assemblyData);
        }};

        when(assembly.createList(false)).thenReturn(list);

        viewModel.onCreate();
        Resource<List<AssemblyData>> value = viewModel.itemsLiveData.getValue();

        Assert.assertNotNull(value);
        Assert.assertEquals(value.status, Resource.Status.SUCCESS);
        Assert.assertEquals(value.data.get(0).getLabelId(), 1);
        Assert.assertEquals(value.data.get(0).getOperationNameId(), 1);

        Mockito.verify(assembly, times(1)).createList(false);
        Mockito.verifyNoMoreInteractions(assembly);
    }

    @Test
    public void onCalculateClickedTest() {
        AssemblyData assemblyData = new AssemblyData(1, 1, true);
        List<AssemblyData> list = new ArrayList<AssemblyData>() {{
            add(assemblyData);
        }};
        AssemblyData assemblyData2 = new AssemblyData(1, 1, false);
        List<AssemblyData> list2 = new ArrayList<AssemblyData>() {{
            add(assemblyData2);
        }};
        int size = 222;

        when(assembly.measureTime(assemblyData, size)).thenReturn(new AssemblyData(1, 1, false, "3"));
        when(assembly.createList(true)).thenReturn(list);
        when(assembly.createList(false)).thenReturn(list2);
        viewModel.onCalculateClicked(String.valueOf(size));

        Resource<List<AssemblyData>> value = viewModel.itemsLiveData.getValue();

        Assert.assertNotNull(value);
        Assert.assertEquals(value.status, Resource.Status.SUCCESS);

        Mockito.verify(assembly, times(1)).createList(true);
        Mockito.verify(assembly, times(1)).createList(false);
        Mockito.verify(assembly, times(1)).measureTime(assemblyData, size);
        Mockito.verifyNoMoreInteractions(assembly);
    }

    @Test
    public void onCalculateClickedWithInvalidDataTest() {
        viewModel.onCalculateClicked("lll");

        Resource<List<AssemblyData>> value = viewModel.itemsLiveData.getValue();

        Assert.assertNotNull(value);
        Assert.assertEquals(value.status, Resource.Status.ERROR);
    }

    @Test
    public void onCalculateClickedWithAlreadyRunningCalculation() {
        AssemblyData assemblyData = new AssemblyData(1, 1, true);
        List<AssemblyData> list = new ArrayList<AssemblyData>() {{
            add(assemblyData);
        }};
        AssemblyData assemblyData2 = new AssemblyData(1, 1, false);
        List<AssemblyData> list2 = new ArrayList<AssemblyData>() {{
            add(assemblyData2);
        }};
        int size = 222;

        when(assembly.measureTime(assemblyData, size)).thenReturn(new AssemblyData(1, 1, false, "3"));
        when(assembly.createList(true)).thenReturn(list);
        when(assembly.createList(false)).thenReturn(list2);

        viewModel.onCalculateClicked(String.valueOf(size));
        viewModel.onCalculateClicked(String.valueOf(size));

        Resource<List<AssemblyData>> value = viewModel.itemsLiveData.getValue();

        Assert.assertNotNull(value);
        Assert.assertEquals(value.status, Resource.Status.SUCCESS);

        Mockito.verify(assembly, times(2)).createList(true);
        Mockito.verify(assembly, times(2)).createList(false);
        Mockito.verify(assembly, times(2)).measureTime(assemblyData, size);
        Mockito.verifyNoMoreInteractions(assembly);
    }

    @Test
    public void getSpanCountCollectionsAssemblyTest() {
        when(assembly.getSpanCount()).thenReturn(3);

        Assert.assertEquals(viewModel.getSpanCount(), 3);

        Mockito.verify(assembly, times(1)).getSpanCount();
        Mockito.verifyNoMoreInteractions(assembly);
    }
}
