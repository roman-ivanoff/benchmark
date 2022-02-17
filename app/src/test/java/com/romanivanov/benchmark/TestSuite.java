package com.romanivanov.benchmark;

import com.romanivanov.benchmark.models.assembly.CollectionsAssemblyTest;
import com.romanivanov.benchmark.models.assembly.MapsAssemblyTest;
import com.romanivanov.benchmark.ui.assembly.AssemblyViewModelTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CollectionsAssemblyTest.class, MapsAssemblyTest.class, AssemblyViewModelTest.class})
public class TestSuite {
}
